//java 11

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ProbFuse {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Historical Results File (csv): ");
        String hRecordsPath = scanner.nextLine();
        System.out.print("Segments to use: ");
        int segCount = Integer.parseInt(scanner.nextLine());
        System.out.print("Live Results File (csv): ");
        String lRecordsPath = scanner.nextLine();
        System.out.print("Results to fuse A B C or D: ");
        Set<String> M = scanner.nextLine().chars().mapToObj(ch -> (char) ch + "").filter("ABCD"::contains).collect(Collectors.toSet());
        if (M.size() <= 1) {
            System.out.println("Invalid Selection. At least two distinct should be selected");
            System.exit(0);
        }

        List<String> hRecords = Files.readAllLines(Paths.get(hRecordsPath))
                .stream()
                .map(s -> s.replace("\uFEFF", ""))//remove BOM character
                .filter(s -> M.contains("" + s.charAt(0)))//only selected input system
                .collect(Collectors.toList());
        Map<String, double[]> trainedProbs = training(hRecords, segCount);
        List<String> lRecords = Files.readAllLines(Paths.get(lRecordsPath))
                .stream()
                .map(s -> s.replace("\uFEFF", ""))//remove BOM character
                .filter(s -> M.contains("" + s.charAt(0)))//only selected input system
                .collect(Collectors.toList());
        Map<String, Map<Integer, Integer>> liveSegs = dLiveSegAll(lRecords, segCount);
        LinkedHashMap<Integer, Double> rankedDocs = fusion(trainedProbs, liveSegs);

        final int[] rank = {0};
        StringBuffer output = new StringBuffer();
        rankedDocs.forEach((key, value) -> {
            String s = String.format("%d,%d,%.5f", ++rank[0], key, value);
            System.out.println(s.replace(",", "\t"));
            output.append(s);
            output.append("\n");
        });
        Calendar c = Calendar.getInstance();
        Files.write(Paths.get(String.format("ranked-docs-%d%d%d-%d%d%d.csv", c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND))), output.toString().getBytes());
    }


    /**
     * Computes probability that document d returned in segment k for a single training query of an input system
     *
     * @param hRecord  a historic relevance record (training query) for an input system
     * @param segCount number of segments to be used (x)
     * @return array where (k-1)th cell is calculated by dividing the number of documents in segment k that are judged
     * to be relevant by the total number of documents in segment k. a[k-1] = |Rk,q|/|k| for k={1,2,3,...}     *
     */
    public static double[] dSegProbability(String hRecord, int segCount) {
        //convert ...R,N,U,R... to ...1,0,0,1...
        List<Integer> collect = Arrays.stream(hRecord.split(","))
                .filter(s -> s.equals("R") || s.equals("U") || s.equals("N"))
                .map(s -> s.equals("R"))
                .map(b -> b ? 1 : 0)
                .collect(Collectors.toList());

        //array to hold a[k-1]
        double[] segs = new double[segCount];

        //length of each segment
        double segLength = collect.size() / (double) segCount;

        //current segment number k
        int k = 1;

        //count of docs in k updated in loop
        int count = 0;

        for (int i = 0; i <= collect.size(); i++) {
            //end of previous segment
            if (i >= k * segLength) {
                segs[k - 1] /= count;
                k++;
                count = 0;
            }
            count++;
            try {
                segs[k - 1] += collect.get(i);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return segs;
    }

    /**
     * Calculates the probability that document d is returned in segment k for a list of training queries of an input
     * system, P(dk|m)
     *
     * @param hRecords list of historic relevance records (training queries) for an input system
     * @param segCount number of segments to be used (x)
     * @return array where (k-1)th cell is calculated by dividing the number of documents in segment k that are judged
     * to be relevant by the total number of documents in segment k, and is averaged over all of the training queries,
     * ie. a[k-1] = (|Rk,q|/|k|)/Q for k={1,2,3,...}
     */
    public static double[] dSegProbabilityAvg(List<String> hRecords, int segCount) {
        double[] segs = new double[segCount];
        for (int i = 0; i <= hRecords.size(); i++) {
            try {
                double[] _segs = dSegProbability(hRecords.get(i), segCount);
                for (int j = 0; j < segs.length; j++) {
                    segs[j] += _segs[j];
                }
            } catch (IndexOutOfBoundsException ex) {
                for (int j = 0; j < segs.length; j++) {
                    segs[j] /= hRecords.size();
                }
            }
        }
        return segs;
    }

    /**
     * Computes the set of probabilities calculated per segment for each input system based on historic relevance
     * records.
     *
     * @param hRecords list of historic relevance records (training queries) for all input systems
     * @param segCount number of segments to be used (x)
     * @return A map of input systems mapped to their respective probabilities
     */
    public static Map<String, double[]> training(List<String> hRecords, int segCount) {
        Map<String, List<String>> map = new HashMap<>();
        hRecords.forEach(s -> {
            //remove BOM character
            s = s.replace("\uFEFF", "");
            //find the system name
            String M = s.substring(0, s.indexOf(","));
            int pos = M.replaceFirst("^(\\D+).*$", "$1").length();
            M = s.substring(0, pos).trim();

            //create a temporary map of input-system name to list of training queries of that input system
            if (!map.containsKey(M))
                map.put(M, new ArrayList<>());
            map.get(M).add(s.substring(s.indexOf(",") + 1));
        });

        //compute average probability of each input system
        Map<String, double[]> probMap = new HashMap<>();
        map.entrySet().forEach(e -> {
            probMap.put(e.getKey(), dSegProbabilityAvg(e.getValue(), segCount));
        });
        return probMap;
    }

    /**
     * Computes the segment number of each document returned by an input system.
     *
     * @param lRecord  live result returned by an input system for some query
     * @param segCount number of segments to be used (x)
     * @return document number mapped to its segment number
     */
    public static Map<Integer, Integer> dLiveSeg(String lRecord, int segCount) {
        //convert string doc numbers to int doc numbers list
        List<Integer> collect = Arrays.stream(lRecord.split(","))
                .map(s -> integer(s))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        //map holds segment number against each doc number
        Map<Integer, Integer> segs = new HashMap<>();

        //length of each segment
        double segLength = collect.size() / (double) segCount;

        //current segment number k
        int k = 1;

        //compute which doc belongs to which segment
        for (int i = 0; i < collect.size(); i++) {
            //end of previous segment
            if (i >= k * segLength)
                k++;
            //map doc number to segment
            segs.put(collect.get(i), k);
        }
        return segs;
    }

    /**
     * Computes the segment number of each document returned by all input systems represented in live file .
     *
     * @param lRecords list of live result returned by each input system for some query
     * @param segCount number of segments to be used (x)
     * @return each input system mapped with their document and segment number
     */
    public static Map<String, Map<Integer, Integer>> dLiveSegAll(List<String> lRecords, int segCount) {
        Map<String, String> map = new HashMap<>();
        lRecords.forEach(s -> {
            //remove BOM character
            s = s.replace("\uFEFF", "");
            //find the system name
            String M = s.substring(0, s.indexOf(",")).trim();
            //create a temporary map of input-system name to sequenced docs returned by that input system
            map.put(M, s.substring(s.indexOf(",") + 1));
        });

        //compute document-segment for all input systems
        Map<String, Map<Integer, Integer>> segMap = new HashMap<>();
        map.entrySet().forEach(e -> {
            segMap.put(e.getKey(), dLiveSeg(e.getValue(), segCount));
        });

        return segMap;
    }

    /**
     * Computes fusion results
     *
     * @param trainedProbs result of <code>training</code> function
     * @param liveSegs     result returned by <code>dSegAll</code> function
     * @return the fused ranked documents mapped to their total final score
     */
    public static LinkedHashMap<Integer, Double> fusion(Map<String, double[]> trainedProbs, Map<String, Map<Integer, Integer>> liveSegs) {
        //map to store document score
        Map<Integer, Double> fuse = new HashMap<>();

        //find all live doc numbers
        Set<Integer> docs = liveSegs.values().parallelStream()
                .map(Map::keySet)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        //calculate score for each doc
        for (Integer d : docs) {
            //based on the segment of the doc in different input system in live result
            for (Map.Entry<String, Map<Integer, Integer>> map : liveSegs.entrySet()) {
                String M = map.getKey();
                int k = map.getValue().getOrDefault(d, 0);
                if (k > 0) {
                    //Sd = SUM( P(dk|m)/k )
                    double P = trainedProbs.get(M)[k - 1];
                    double Sd = fuse.getOrDefault(d, 0d) + P / k;
                    fuse.put(d, Sd);
                }
            }
        }

        //rank (sort) documents in descending order of their score
        LinkedHashMap<Integer, Double> rank = new LinkedHashMap<>();
        fuse.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> rank.put(entry.getKey(), entry.getValue()));

        return rank;
    }

    /**
     * Convert string to integer
     *
     * @param s
     * @return integer of s if possible, null otherwise
     */
    public static Integer integer(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static void test() {
        //training

        training(List.of(
                "A1,R,R,R,R,N,R,N,N,R,N,N,N",
                "A2,R,U,R,R,N,U,R,U,U,U,U,U",
                "A3,U,R,N,R,N,N,N,U,U,U,U,R"),
                4)
                .forEach((key, value) -> System.out.println(String.format("%s=%s", key, Arrays.toString(value))));

        //fusion

        Map<String, double[]> training = Map.of(
                "A", new double[]{.75, .67, .33, .10},
                "B", new double[]{.67, .50, .30, .00},
                "C", new double[]{.90, .55, .26, .15});

        Map<String, Map<Integer, Integer>> live = dLiveSegAll(List.of(
                "A,10,4,6,14,2,8,1,3,15,5,7,12",
                "B,7,1,3,8,11,12,16,2,9,5,4,13",
                "C,1,5,7,4,3,12,11,8,6,2,10,9"), 4);

        fusion(training, live).forEach((key, value) -> System.out.println(String.format("%d=%f", key, value)));

    }
}
