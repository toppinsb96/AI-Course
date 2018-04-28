library(nnet)
library(caret)
library(class)

data <- read.csv('ttrain.csv',head=TRUE,sep=",")
finaltest <- read.csv('test.csv',head=TRUE,sep=",")

mod <- nnet(Survived~PassengerId+Pclass+Sex+Age+SibSp+Parch+Fare, data,size=10,skip=FALSE,linout=FALSE)

summary(mod)
data_test = subset(data, select=-Survived)


pred <- predict(mod, data_test, type="class")
table(pred,data$Survived)

grid <- expand.grid(size=c(6,7,8,9,10,11,12,13,14,15), decay=c(0,0.01,0.1,1))
nfit <-train(Survived~PassengerId+Pclass+Sex+Age+SibSp+Parch+Fare, data=data, method="nnet",tuneGrid=grid,skip=FALSE,linout=FALSE)

pred3 <- predict(nfit, finaltest, type="raw")
pred3
nfit
