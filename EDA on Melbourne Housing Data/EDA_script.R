---
Title: "Big Data Analysis"
Author: "Lalith Venkat Perugu"
Date: "3/29/2020"
Output:
  word_document: default
  pdf_document: default
  html_document:
    df_print: paged
---

```{r setup, include=FALSE}
knitr::opts_chunk$set(echo = TRUE)
if(!require("plotrix")) install.packages("plotrix");
library("plotrix")
if (!require('dplyr')) install.packages('dplyr'); library('dplyr')
# check for and load the required packages

if (!require('ggplot2')) install.packages('ggplot2'); library('ggplot2')
```

## Loading the Melbourne Data set
```{r}
housing<- read.csv("C:/Program Files/R/melbourne_data.csv")
str(housing)
```
## Task
### Firstly the dataset should be cleaned and make it available for analysis by removing/replacings the NAs,outliers and incorrect values

#### Removing the NA values 
```{r}
housing<-na.omit(housing)
```
#### na.omit() fucntion is used to remove all the NA values from the dataset.
#### we can observe that the data set is large and there are multiple varaibles with many outliners.
#### For better visualisation we have replaced those outliners for some of the varaiables with the below code.
#### variable Landsize
```{r}
housing[housing$Landsize < 100, "Landsize"]<- 250
housing[housing$Landsize >1500,"Landsize"]<- 500
```
#### Car Varaiable
```{r}
housing= housing[!housing$Car<1,]
housing[housing$Car>6,"Car"]<-6
```
#### Price variable
```{r}
housing$Price<-replace(housing$Price, housing$Price>2000000,1600000)
```
#### Distance variable
```{r}
housing$Distance<-droplevels(housing$Distance)
housing$Distance=as.numeric(as.character(housing$Distance))
```
#### Regionname varaiable
```{r}
housing$Regionname<-droplevels(housing$Regionname)
```
#### propertycount variable
```{r}
housing$Propertycount<-droplevels(housing$Propertycount)
housing$Propertycount<-as.numeric(as.character(housing$Propertycount))
```
#### The dataset after removing the outliners and incorrect/missing values
```{r}
str(housing)
```
### 2) summary of the dataset gives the mean , meadian,1st Quartile,3rd Quartile,maximum,minimun values for all numerical variables
```{r}
summary(housing)
```
#### Here i have prepared 4 graphs with different variables
### Pie chart
```{r}
mytable <- table(housing$Type)
lbls <- paste(names(mytable), "\n", mytable, sep="")
pie3D(mytable, labels = lbls,radius =1,
      main="Pie Chart of Species\n (h=house, u=unit/duplex, t=townhouse)",col= c("blue","green","red"))
```

#### The above pie chart is for the House types and their respective count. 
####  Here i have used plotrix package to form a 3D pie chart

## Bar Chart
```{r}
barplot(table(housing$Regionname), main= "Bar Garph for Region names", xlab="Region names",ylab="count",col=(1:7),legend=rownames(housing$Regionname))

```

#### The above Bar graph is for Region names

## Histogram
```{r}
h<-hist(housing$Landsize,main="Landsize in meters",xlab = "Landsize",xlim = c(100,700),ylim = c(100,1000),col = "darkmagenta",freq = TRUE)
text(h$mids,h$counts,labels=h$counts,adj=c(0.5, -0.5))
```

#### The above Histogram is for Landsize in meters

## scatterplot
```{r}
a<-housing$Rooms
b<-housing$Bathroom
plot(a,b, main = "scatter plot for Rooms and Bathroom",xlab = "No ofRooms",ylab = "No of bathrooms",pch=10)
abline(lm(b~a), col="red")
```

## 3) Analysis on Price Variable
### 3a) Histogram for price variable
```{r}
hist(housing$Price,xlim = c(50000,2000000),main= "Histogram for Price",xlab="Price",col=(1:7))
```
```{r}
summary(housing$Price)
```

#### summary() gives the mean and median for the price varaiable along with other attributes

```{r}
var(housing$Price)
```

### The var() gives the varience for the price variable

## 3b) Grouping The Houses by price ranges such as Low, High, Medium
### Here i have installed dplyr package to use the group_by()
### I have used the mutate() to create a new variable called state.
### I have obtained the summary for the groups.
```{r}
y<-housing %>% group_by(Type) %>% mutate(state= cut(Price,breaks=3,labels=c("Low","Medium","high"),include.lowest = TRUE))
summary(y$state)
```

## 3c) Exploring the prices for different house types using boxplot
```{r}
housetype<-c("h","t","u")
boxplot(housing$Price[housing$Type=="h"],housing$Price[housing$Type=="t"],housing$Price[housing$Type=="u"],main= "exploring the Price for different house 
types",ylim=c(30000,2000000),col = c("blue","red","orange"),xlab="House Type",ylab="Range",names = housetype)
```

### 3d)The variables that are most correlated with price
```{r}
cor(housing$Price,housing$Rooms, method = "pearson")
cor(housing$Price,housing$Distance, method = "pearson")
cor(housing$Price,housing$Propertycount, method = "pearson")
cor(housing$Price,housing$Bathroom, method = "pearson")
cor(housing$Price,housing$Car, method = "pearson")
cor(housing$Price,housing$Landsize, method = "pearson")
cor(housing$Price,housing$BuildingArea, method = "pearson")
cor(housing$Price,housing$YearBuilt, method = "pearson")
```

### By using cor() with price and all the other variables we can observer that Rooms,Bathroom,BuildingArea are the three variables most correlated with price.

## 4)Listing the frequencies of various house type
```{r}
table(housing$Type)
```

#### From the above we can see frequencies of various house types
### Scatter plots
### scatter plot of houseprice by landsize
### here i have installed gglpot2 package to use the ggplot function
```{r}
ggplot(housing,aes(Price,BuildingArea, col = Landsize),xlim(500000,2000000),ylim(0,500))+ geom_point()+ggtitle("House price by landsize")
```

### Scatter plot of Houseprice by Type
```{r}
ggplot(housing,aes(Price,BuildingArea, col = Type))+ geom_point()+ggtitle("House price by Type")
```

