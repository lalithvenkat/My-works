---
title: "BDA Assignment 2"
author: "Lalith Venkat Perugu"
date: "11/01/2021"
output: github_document
---
# Loading the Melbourne data set.
```{r echo=TRUE}
housingdata <- read.csv("C:/Program Files/R/melbourne_housing_data.csv")
```

### Lets see the structure of the dataset to know the no of objects and variables present
```{r echo= TRUE}
str(housingdata)
```

```{r setup, include=FALSE}
knitr::opts_chunk$set(echo = FALSE)
if(!require(tinytex)) install.packages("tinytex", repos = "http://cran.us.r-project.org")
if(!require("plotrix")) install.packages("plotrix", repos = "http://cran.us.r-project.org");
library("plotrix")
if(!require('ggplot2')) install.packages("ggplot2", repos ="http://cran.us.r-project.org")
library('ggplot2')
if(!require('BSDA'))
  install.packages("BSDA", repos="http://cran.us.r-project.org")
library(BSDA)
if(!require('dplyr')) install.packages("dplyr",repos="http://cran.us.r-project.org")
library("dplyr")
if(!require('car'))
  install.packages("car",repos="http://cran.us.r-project.org")
library("car")
if(!require('caret'))
install.packages("caret",repos="http://cran.us.r-project.org")
library("caret")
if(!require('corrplot'))
  install.packages("corrplot",repos="http://cran.us.r-project.org")
library('corrplot')
if(!require('neuralnet'))
  install.packages("neuralnet",repos="http://cran.us.r-project.org" )
library("neuralnet")
if(!require('C50'))
  install.packages("C50",repos="http://cran.us.r-project.org")
library('C50')
```
```{r echo=TRUE} 
summary(housingdata)
```
# Task A
## Hypothesis 1


## one sample Test 
From the housing data set we take the  price variable and lets define the hypothesis on house prices with respective to the house types h and u.
Lets go with the Z test as we have the mean and standard deviation for the house prices.

## 1 Defining the hypothesis   

### Null hypothesis Ho: The Average price of the houses with     rescpective to the house type h is equal to the Average price of the houses with house type u

### i.e Ho = mu1-mu2=0

### Alternate Hypothesis H1: The Average price of the houses with     rescpective to the house type h is not equal to the Average price of the houses with house type u
### i.e H1: mu1-mu1 not equal to zero

## 2 State Alpha:
### Lets take the significance level as 0.05
## 3 Confidence level = 95
## 4 Decision Rule:
### If the z value is less than -1.96 or greater than 1.96,Reject the null hypothesis.
## Test statestic 
### We use the z test method as we have the sample size greater than 30 , and also the standard deviation is known.
### Here we are using two samples of different house type prices.
```{r echo=TRUE}

typeh<-subset(housingdata$Price, housingdata$Type=="h")
length(typeh)
mean(typeh)
sd(typeh)
typeu<-subset(housingdata$Price,housingdata$Type=="u")
mean(typeu)
sd(typeu)
length(typeu)
```

```{r echo=TRUE}
set.seed(100)
sampleoftypeh<-sample(typeh,50,replace = FALSE)
sampleoftypeu<-sample(typeh,50,replace = FALSE)
```

```{r echo=TRUE}
mu1=mean(sampleoftypeh)
mu2=mean(sampleoftypeu)
mu=mu1-mu2
mu
z.test(sampleoftypeh,sampleoftypeu,alternative = "two.sided",mu=0,sigma.x = sd(sampleoftypeh),sigma.y = sd(sampleoftypeu),conf.level = 0.95 )

```
## Conclusion: From the above two sample z test using z.test function we can notice that  z value is lies between  -1.96 and +1.96, Which means we should not reject the null hypothesis according to the Decision Rule.
## Also we can also find the p value is greater than the siginificane value (Alpha value), Which confirms that we should not reject the Null hypothesis
## From the output we can also notice that the true difference in means of two samples is not equal to 0.
## The output also provides that sample mean estimates of two samples.
## Here we have BSDA package to use the z test function.

```{r echo=TRUE}
housetype<-c("h","u")
boxplot(housingdata$Price[housingdata$Type=="h"],
housingdata$Price[housingdata$Type=="t"],main= "exploring the Price for different house
types",ylim=c(30000,2000000),col = c("blue","red","orange"),xlab="House Type"
,ylab="Range of Prices",names = housetype)

```
## From the graph we can notice that varience of house price for house type h is double than the variance of house price for type u.











```{r echo=TRUE}
mean(housingdata$Price)
sd(housingdata$Price)
```


```{r echo=TRUE}
housingdata1<-housingdata
sample1<-housingdata$Price
sample2<-housingdata$Type
sample2<-as.factor(sample2)
levels(sample2)
class(sample1)

```
# Hypothesis 2:
##  two sample z test
From the housing data set we take the  price variable and lets define the hypothesis on house prices with respective to the no of rooms that house has. Here we are considering the houses that contain only 2 and 3 rooms. We take two samples and make the 
conclusion for the following hypothesis.
## Defining Hypothesis

## Null Hypothesis: The Average price of houses with only 2 rooms is less than or equal to the average price of the houses with only 3 rooms.
i.e Ho: u<= u1

## Alternate Hypothesis: The Average price of houses with only 2 rooms is greater than the average price of the houses with only 3 rooms.
i.e H1: u>u1

## State Alpha
The significance level(alpha) is defined as 0.05

## Confidence level is defined as 95

## Decision Rule:If the z value is less than -1.96 or greater than 1.96,Reject the null hypothesis.

## Test statistic:We use the z test method as we have the sample size greater than 30 , and also the standard deviation is known.

## Here we are using two samples of with respect to no of rooms

```{r echo=TRUE}
boxplot(housingdata$Price~housingdata$Rooms)
library("car")
rooms2<-subset(housingdata$Price, housingdata$Rooms==2)
length(rooms2)
mean(rooms2)
rooms3<-subset(housingdata$Price,housingdata$Rooms==3)
length(rooms3)
mean(rooms3)
```

```{r echo=TRUE}
library(BSDA)
set.seed(120)
rooms2sample<-sample(rooms2,50,replace = FALSE)
rooms3sample<-sample(rooms3,50,replace= FALSE)
mu=mean(rooms3)-mean(rooms2)
z.test(rooms2sample,rooms3sample,alternative="less",mu=212435.4,sigma.x = sd(rooms2sample),sigma.y = sd(rooms3sample),conf.level = 0.95)

```
## From the output we can notice that the Z value is -6.5124.
## Conclusion
## The Z values doesn't lie between the -1.96 and +1.96 as stated in decision rule, Hence we should reject the null hypothesis.




```{r echo=TRUE}
noofrooms<-c(2,3)
boxplot(housingdata$Price[housingdata$Rooms==2],housingdata$Price[housingdata$Rooms==3],main="exploring the house price depending the no of rooms",ylim=c(90000,3000000),col=c("blue","orange"),xlab="No of Rooms", ylab="Range of Price",noofrooms)

```
## From the graph we can observe that the house with only two rooms has less mean price than the houses with 3 rooms.


# Hypothesis 3
# One sample t test
To determine whether the sample mean of prices and mean of prices are equal.
## 1 Define Hypothesis
## Null hypothesis HO: To check whether the sample mean of house prices are equal to the mean of the house prices.
## Ho= 997898.2

## Alternate Hpyothesis: To check whether the sample mean of house are not equal to the mean of house prices
## H1 not equal to 997898.2

## Alpha = 0.05
## confidence level =95
## Decision Rule: Reject null hypothesis if t > 2.26 and t<2.26
## Test statistic: even though we assume to know the mean og the house price, we use t-test , as we assume that our sample is less than 30.

```{r echo=TRUE}
sampledata<-sample(housingdata$Price ,30,replace = FALSE)
sd(sampledata)
```
```{r echo=TRUE}
t.test(sampledata,alternative="two.sided",mu=997898.2,conf.level = 0.95)
```
## conclusion: From the above output we can notice that t value is -0.3442 and p value =0.733
## As t value lies betwwn the -2.26 and +2.26 as stated in the decision rule, We can confirm that we don't reject the null hypothesis.
## It also provides values  which are from 765713.3 and 1163153.4 for 95 confidence interval



# Hypothesis 4
# Linear Regression between the price and distance
## 1 Define hypothesis:
## Null Hypothesis Ho= There is linear relation between price and distance
## Alternative Hypothesis H1 = There is no linear relation between price and distance.

```{r echo=TRUE}
lmod<-lm(Price ~ Distance, data=housingdata)
summary(lmod)
```

## From the above output we see R-squared value of 0.06433 
## Conclusion: From the above finding's with respect to R-squared value which is 0.06433 tells that there is very less variance for price and distance. As the p value 2.2e-16<0.05 suggests that this overall a good model. But there are also many other variables which have a significant relantionship with price.

```


#Task 2

The task is to split the data into training data and test data with a split percentage of 75/25.
First lets load the caret library, which contains many functions for modeling training process in regression and classification.

## Performing linear regression with multiple variables to predict the house price

```{r echo=TRUE}
library(caret)
set.seed(100)  
## Setting a seed number ensures that we get the same result when we run this process with the same seed.

##splitting the data set into training data and test data
trainingindex <- createDataPartition(housingdata$Price, p= 0.75, list = F)
trainingdata<-housingdata[trainingindex,]
testdata<-housingdata[-trainingindex,]

linear_model<-lm(Price~Rooms+Type+Distance+Postcode+Regionname+Propertycount,data=housingdata)
linear_model
summary(linear_model)
coef(linear_model)
prediction1 <-predict(linear_model, newdata = housingdata)
traindata <- lm(Price~Rooms+Type+Distance+Postcode+Regionname+Propertycount,data = trainingdata)

coef(traindata)
prediction1 <- predict(traindata, newdata = testdata)
summary(prediction1)

```


```{r echo=TRUE}
trainingdata1<-trainingdata

```

Here we have replicated training data as trainingdata1 as a precautionary to not disturb the original training data while trying to know the possible variables for predicting the house price using corrplot

```{r echo=TRUE}
str(trainingdata1)
library(corrplot)
sapply(trainingdata1,class)
i<-c(1,2,3,4,5,6,7,8,9,10,11,12,14)
trainingdata1[, i]<-apply(trainingdata1[, i],2,function(x) as.numeric(as.character(x)))
A<-cor(trainingdata1)
corrplot(A,method = "circle")
```
## From the above corrplot we can confirm that the variables Rooms,Type and Distance are most associated with the price variable.


```{r echo=TRUE}
cor(prediction1,testdata$Price)
```
```{r echo=TRUE}
cor(prediction1,testdata$Price)^2
```
```{r echo=TRUE}
RMSE(testdata$Price, prediction1)
```

## Here we can find that the Adjusted R squared value is 0.5212 and the multiple R-squared value is 0.5213.
## The R-squared value gives the propotion of variance for dependent variable with respect to indepedent variables.
## The prediction accuracy of the model on the test data with respect to RMSE and correlation is 411240.8 and 0.5328335

## Normalization of data

Here we are using min-max scaling normalization to bring all variables in same range.

```{r echo=TRUE}
normal <- preProcess(housingdata[,c(4:6,10:13)], method=c("range"))
normdata <- predict(normal, housingdata[,c(4:6,10:13)])
summary(normdata)

```

```{r echo=TRUE}
library("caret")
set.seed(116)
trainingindex2 <- createDataPartition(normdata$Price, p= 0.75, list = F)
trainingdata2 <- normdata[trainingindex2,]
testdata2 <- normdata[-trainingindex2,]

```
Performing linear regression with multiple variables to predict the house price

```{r echo=TRUE}
linear_model2<-lm(Price~Rooms+Type+Distance+Postcode+Regionname+Propertycount,data=normdata)
linear_model2
summary(linear_model2)
coef(linear_model2)
#Testing the training data againist the test data
ins_predict2<-predict(linear_model2, newdata = testdata2)
summary(ins_predict2)
```
```{r echo=TRUE}
cor(ins_predict2,testdata2$Price)
```
```{r echo=TRUE}
cor(ins_predict2,testdata2$Price)^2
```
```{r echo=TRUE}
RMSE(testdata2$Price, ins_predict2)

```
From the normalized data we can observe that there is very slight differences in the values. The Adjusted and multiple R-squared value are 0.5236,0.5238 which is very similar the R-squared values 0.5212 and 0.5213
The p values remains same for the orginal data and normalized data.
The main difference between these models are RMSE value.
The Rmse value for the normalized data is 0.036820 which is very low with respect the rsme value of original data.


# Task 3

## Dividing the dataset
Here we are splitting the data set into training data set and test data set with ratio of 80/20.
we also peform normalization on the data set for better results.

```{r echo=TRUE}
housingdata.sub1<-housingdata[,c(4,5,6,7,12,13)]
samplesize <- sample(nrow(housingdata.sub1), size=1000, replace = FALSE, prob = NULL)
housingdata.sub2 <- housingdata.sub1[samplesize, ]
indxdata <- createDataPartition(y = housingdata.sub2$Type,p = 0.8,list = FALSE)
training5 <- housingdata.sub2[indxdata,]
testing5 <- housingdata.sub2[-indxdata,]

# Run k-NN:
set.seed(200)
ctrl <- trainControl(method="repeatedcv",repeats = 3)
knnFit <- train(Type ~ ., data = training5, method = "knn", trControl = ctrl, preProcess = c("center","scale"),tuneLength = 5)
knnFit

#Plotting different k values against accuracy (based on repeated cross validation)
plot(knnFit)
confusionMatrix(knnFit)

```

## From the above output we can observe that the knn with 801 samples from a dataset has 5 predictors and 3 classes which are categorized by house types.
## We can observe the respective accuracy for knn with respect to change in the value of k
## Thus the Accuracy for knn is 79

# 2) C5.0
using c5.0 algorithm to classify house types into appropriate types based on their features.

```{r echo=TRUE}
library(caret)
library(C50)
library(lattice)
library(ggplot2)
housingdata.subset1<-housingdata[,c(4,5,6,7,12,13)]
samplesize <- sample(nrow(housingdata.subset1), size=500, replace = FALSE, prob = NULL)
housingdata.subset <- housingdata.subset1[samplesize, ]
housingdata.subset$Type<- as.factor(housingdata.subset$Type)

trainingindex <- createDataPartition(housingdata.subset$Type, p= 0.8, list = F)
c50_training <- housingdata.subset[trainingindex,]
c50_test <- housingdata.subset[-trainingindex,]
C5_fit <- train(Type~., data = c50_training, method = "C5.0")
summary(C5_fit)

```

we can observe that there are 401 samples with 5 predictor and 3 classes.
It shows the classification of model types winnow and trails.
the final value used for the model were trials = 20, model =tree and winnow =False
```{r echo=TRUE}
C5_predict <- predict(C5_fit, newdata = c50_test )
confusionMatrix(C5_predict, c50_test$Type )

```
we can observe that from the above output c50 algorithm has an accuracy of 85 for the following data.
`

# 3 ANN
The artificial neural networks models the relantionship between input signals and output signal using a model
```{r echo= TRUE}
library("neuralnet")

housingdata.set<-housingdata[,c(4,6,12)]
housingdata.set = housingdata.set[complete.cases(housingdata.set),]
housingdata.set<-na.omit(housingdata.set)
a=apply(housingdata.set, 2, function(x) any(is.na(x) | is.infinite(x)))

normalize <- function(x) {
return((x - min(x)) / (max(x) - min(x)))
}
housingdata.set <- as.data.frame(lapply(housingdata.set, normalize))
train_index <-sample(nrow(housingdata.set),2/3 *nrow(housingdata.set))
A_training <- housingdata.set[train_index,]
B_test <- housingdata.set[-train_index,]
A_fit <- neuralnet(Price~., data = A_training)
plot(A_fit)
A_results<-compute(A_fit,B_test)
p_strength<-A_results$net.result
cor(p_strength,B_test$Price)
## We have obtained the correlation.



```




```

```