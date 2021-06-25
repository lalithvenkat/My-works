{
 "cells": [
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "### Part1"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {},
   "outputs": [
    {
     "name": "stdout",
     "output_type": "stream",
     "text": [
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:13:25\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:13:30\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:13:35\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:13:40\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:13:45\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:13:50\n",
      "-------------------------------------------\n",
      "\n"
     ]
    }
   ],
   "source": [
    "from pyspark import SparkContext\n",
    "from pyspark.streaming import StreamingContext\n",
    "from pyspark.sql.types import StructType\n",
    "\n",
    "sc = SparkContext(appName=\"PysparkStreaming\")\n",
    "ssc = StreamingContext(sc, 5)   # Streaming will execute in each 5 seconds\n",
    "\n",
    "schema = StructType().add(\"year\", \"int\").add(\"month\", \"string\") \n",
    "    .add(\"day\", \"int\").add(\"site\", \"int\").add(\"sumofsumvolume\", \"int\")\n",
    "    \n",
    "df = spark.readStream.option(\"sep\",\",\").option(\"header\", \"false\").schema(schema).csv(\"part1/\")\n",
    "    \n",
    "df.registerTempTable(\"tbl_scats_volume\")\n",
    "\n",
    "query = \"\"\"select region, Total_Traffic_Volume from  (\n",
    "select s1.region,count(tv.SumOfSumVolume) as Total_Traffic_Volume\n",
    "from tbl_scats_volume tv\n",
    "inner join  tbl_scats_sites s1 \n",
    "on tv.site=s1.siteid\n",
    "group by s1.region \n",
    ")s\n",
    "order by Total_Traffic_Volume desc\"\"\"\n",
    "\n",
    "df = spark.sql(query)\n",
    "\n",
    "df.write.format(\"org.apache.spark.sql.cassandra\")\\\n",
    ".options(table=\"part1\", keyspace=\"assignment3\")\\\n",
    ".save(mode=\"append\")\n",
    "\n",
    "ssc.start()\n",
    "ssc.awaitTerminationOrTimeout(30)"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "### Part 2"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 1,
   "metadata": {},
   "outputs": [
    {
     "name": "stdout",
     "output_type": "stream",
     "text": [
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:35:00\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:35:05\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:35:10\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:35:15\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:35:20\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:35:25\n",
      "-------------------------------------------\n",
      "\n"
     ]
    },
    {
     "data": {
      "text/plain": [
       "False"
      ]
     },
     "execution_count": 1,
     "metadata": {},
     "output_type": "execute_result"
    }
   ],
   "source": [
    "from pyspark import SparkContext\n",
    "from pyspark.streaming import StreamingContext\n",
    "from pyspark.sql.types import StructType\n",
    "\n",
    "sc = SparkContext(appName=\"PysparkStreaming\")\n",
    "ssc = StreamingContext(sc, 5)   # Streaming will execute in each 5 seconds\n",
    "\n",
    "schema = StructType().add(\"siteid\", \"string\").add(\"site_description_cap\", \"string\") \n",
    "    .add(\"site_description_lower\", \"string\").add(\"region\", \"string\").add(\"lat\", \"string\").add(\"long\", \"string\")\n",
    "    \n",
    "df = spark.readStream.option(\"sep\",\",\").option(\"header\", \"false\").schema(schema).csv(\"part2/\")\n",
    "df.registerTempTable(\"tbl_scats_sites\")\n",
    "\n",
    "schema = StructType().add(\"end_time\", \"string\").add(\"region\", \"string\") \\\n",
    "    .add(\"site\", \"int\").add(\"detector\", \"int\").add(\"sum_volume\", \"double\").add(\"avg_volume\", \"double\") \\\n",
    "    .add(\"weighted_avg\", \"double\").add(\"weighted_var\", \"double\").add(\"weighted_std_dev\", \"double\")\n",
    "\n",
    "df_scats_detector = spark.readStream.option(\"sep\",\",\").option(\"header\", \"false\").schema(schema).csv(\"scats_detector/\")\n",
    "df_scats_detector.registerTempTable(\"tbl_scats_volume_202002\")\n",
    "\n",
    "Hour_df = spark.sql(\"select EXTRACT(Hour from end_time) as Hour, \\\n",
    "Region,cast(Site as int),cast(Detector as int),cast(Sum_volume as int), cast(avg_volume as int) \\\n",
    "from tbl_scats_volume_202002\")\n",
    "Hour_df.createOrReplaceTempView(\"Refined_df\")\n",
    "\n",
    "\n",
    "Morning_7 = spark.sql(\"select Region,Site,Hour, Total_Hourly_Traffic_Volume from ( \\\n",
    "select Region,Site, Hour, sum(Sum_Volume) Total_Hourly_Traffic_Volume \\\n",
    "from Refined_df  \\\n",
    "group by Region,Site,Hour \\\n",
    ")Morning \\\n",
    "where Hour >= 6 and Hour<=11\")\n",
    "\n",
    "Evening_7 = spark.sql(\"select Region,Site,Hour, Total_Hourly_Traffic_Volume from ( \\\n",
    "select Region,Site, Hour, sum(Sum_Volume) Total_Hourly_Traffic_Volume \\\n",
    "from Refined_df  \\\n",
    "group by Region,Site,Hour \\\n",
    ")Morning \\\n",
    "where Hour >= 15 and Hour<=19\")\n",
    "\n",
    "Morning_7.createOrReplaceTempView(\"Morning_7\")\n",
    "Evening_7.createOrReplaceTempView(\"Evening_7\")\n",
    "\n",
    "query = \"\"\"\n",
    "(select Region , busiest_sites,\"Morning\" as type from (\n",
    "select row_number() over(partition by a.Region  order by  a.Total_Hourly_Traffic_Volume desc) row_num, \n",
    "a.Region,a.Site as busiest_sites,a.Total_Hourly_Traffic_Volume from Morning_7 a inner join \n",
    "(select Region,Site, max(Total_Hourly_Traffic_Volume) as Peak_Volume from Morning_7\n",
    "where Total_Hourly_Traffic_Volume<>0\n",
    "group by Region,Site)b \n",
    "on a.Site=b.Site and a.Total_Hourly_Traffic_Volume=b.Peak_Volume and a.Region=b.Region\n",
    "where Total_Hourly_Traffic_Volume<>0 \n",
    ")s\n",
    "where row_num in (1,2,3))\n",
    "union all\n",
    "(select distinct Region , busiest_sites,\"Evening\" as type from (\n",
    "select row_number() over(partition by a.Region  order by  a.Total_Hourly_Traffic_Volume desc) row_num, \n",
    "a.Region,a.Site as busiest_sites,a.Total_Hourly_Traffic_Volume from Evening_7 a inner join \n",
    "(select Region,Site, max(Total_Hourly_Traffic_Volume) as Peak_Volume from Evening_7\n",
    "where Total_Hourly_Traffic_Volume<>0\n",
    "group by Region,Site)b \n",
    "on a.Site=b.Site and a.Total_Hourly_Traffic_Volume=b.Peak_Volume and a.Region=b.Region\n",
    "where Total_Hourly_Traffic_Volume<>0 \n",
    ")s\n",
    "where row_num in (1,2,3))\n",
    "\"\"\"\n",
    "\n",
    "df = spark.sql(query)\n",
    "\n",
    "df.write.format(\"org.apache.spark.sql.cassandra\")\\\n",
    ".options(table=\"part2\", keyspace=\"assignment3\")\\\n",
    ".save(mode=\"append\")\n",
    "\n",
    "ssc.start()\n",
    "ssc.awaitTerminationOrTimeout(30)"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "### Part 3a"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 1,
   "metadata": {},
   "outputs": [
    {
     "name": "stdout",
     "output_type": "stream",
     "text": [
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:50:30\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:50:35\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:50:40\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:50:45\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:50:50\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:50:55\n",
      "-------------------------------------------\n",
      "\n"
     ]
    },
    {
     "data": {
      "text/plain": [
       "False"
      ]
     },
     "execution_count": 1,
     "metadata": {},
     "output_type": "execute_result"
    }
   ],
   "source": [
    "from pyspark import SparkContext\n",
    "from pyspark.streaming import StreamingContext\n",
    "from pyspark.sql.types import StructType\n",
    "\n",
    "sc = SparkContext(appName=\"PysparkStreaming\")\n",
    "ssc = StreamingContext(sc, 5)   # Streaming will execute in each 5 seconds\n",
    "    \n",
    "schema = StructType().add(\"year\", \"int\").add(\"month\", \"string\") \n",
    "    .add(\"day\", \"int\").add(\"site\", \"int\").add(\"sumofsumvolume\", \"int\")\n",
    "    \n",
    "df = spark.readStream.option(\"sep\",\",\").option(\"header\", \"false\").schema(schema).csv(\"part1/\")\n",
    "    \n",
    "df.registerTempTable(\"tbl_scats_volume\")\n",
    "\n",
    "\n",
    "query = \"\"\"select year, month, total_Volume from (\n",
    "select year,month, sum(SumOfSumVolume) total_Volume\t\n",
    "from tbl_scats_volume\n",
    "group by year,month\n",
    ")s\n",
    "where year is not null\n",
    "order by total_Volume desc\n",
    "\"\"\"\n",
    "\n",
    "df = spark.sql(query)\n",
    "\n",
    "df.write.format(\"org.apache.spark.sql.cassandra\")\\\n",
    ".options(table=\"part3a\", keyspace=\"assignment3\")\\\n",
    ".save(mode=\"append\")\n",
    "\n",
    "ssc.start()\n",
    "ssc.awaitTerminationOrTimeout(30)"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "### Part 3b"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 1,
   "metadata": {},
   "outputs": [
    {
     "name": "stdout",
     "output_type": "stream",
     "text": [
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:53:15\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:53:20\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:53:25\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:53:30\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:53:35\n",
      "-------------------------------------------\n",
      "\n",
      "-------------------------------------------\n",
      "Time: 2021-01-22 01:53:40\n",
      "-------------------------------------------\n",
      "\n"
     ]
    },
    {
     "data": {
      "text/plain": [
       "False"
      ]
     },
     "execution_count": 1,
     "metadata": {},
     "output_type": "execute_result"
    }
   ],
   "source": [
    "from pyspark import SparkContext\n",
    "from pyspark.streaming import StreamingContext\n",
    "from pyspark.sql.types import StructType\n",
    "\n",
    "sc = SparkContext(appName=\"PysparkStreaming\")\n",
    "ssc = StreamingContext(sc, 5)   # Streaming will execute in each 5 seconds\n",
    "    \n",
    "schema = StructType().add(\"year\", \"int\").add(\"month\", \"string\") \n",
    "    .add(\"day\", \"int\").add(\"site\", \"int\").add(\"sumofsumvolume\", \"int\")\n",
    "    \n",
    "df = spark.readStream.option(\"sep\",\",\").option(\"header\", \"false\").schema(schema).csv(\"part1/\")\n",
    "    \n",
    "df.registerTempTable(\"tbl_scats_volume\")\n",
    "\n",
    "\n",
    "query = \"\"\"select year, day, avg_Volume from (\n",
    "select year,day, avg(SumOfSumVolume) avg_Volume\t\n",
    "from tbl_scats_volume\n",
    "group by year,day\n",
    ")s\n",
    "where year is not null\n",
    "order by avg_Volume desc\n",
    "\"\"\"\n",
    "\n",
    "df = spark.sql(query)\n",
    "\n",
    "df.write.format(\"org.apache.spark.sql.cassandra\")\\\n",
    ".options(table=\"part3b\", keyspace=\"assignment3\")\\\n",
    ".save(mode=\"append\")\n",
    "\n",
    "ssc.start()\n",
    "ssc.awaitTerminationOrTimeout(30)"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {},
   "outputs": [],
   "source": []
  }
 ],
 "metadata": {
  "kernelspec": {
   "display_name": "Python 3",
   "language": "python",
   "name": "python3"
  },
  "language_info": {
   "codemirror_mode": {
    "name": "ipython",
    "version": 3
   },
   "file_extension": ".py",
   "mimetype": "text/x-python",
   "name": "python",
   "nbconvert_exporter": "python",
   "pygments_lexer": "ipython3",
   "version": "3.6.5"
  }
 },
 "nbformat": 4,
 "nbformat_minor": 2
}
