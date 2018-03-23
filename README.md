

# Assignment6
## Feature Sets
### First Feature Set
The first feature set I tried was simple:

|Feature|
|---|
|prevToken|
|curToken|
|nextToken|
|curPOS|
|nextPOS|
|prevChunkTag|
|curChunkTag|
|nextChunkTag|

However as I was adding features, I found that using previous part of speech tag as a feature will lower the F1 score, so I remove that from the feature set. This simple set of features gave an F1 score of 71.20.
![1](https://github.com/hming9527/Assignment6/blob/master/out/1.png)

### Second Feature Set
The second feature set I tried was adding previous name tag as a feature based on my first feature:

|prevNameTag|
|---|

This simple addition gave an F1 score of 76.01.

![2](https://github.com/hming9527/Assignment6/blob/master/out/2.png)

### Third Feature Set
After the first two simple feature sets, I tried to add some features based on common sense. My first thought was that a word which has a capital initial letter and is not the first word of a sentence is more likely to be classified as a name, So I added these two extra features.

|capitalInitial|isFirstWord|
|---|---|

This gave an F1 score of 79.01.

![3](https://github.com/hming9527/Assignment6/blob/master/out/3.png)

### Fourth Feature Set
My fourth try is an improvement to the last try. As I looked into the data set, I noticed there were certain exceptions. Tokens that contain only capital letters, or numbers and special characters, or is present in the exclusion list I generated, are not likely to be classified as a name. The exclusion list I used basically just lists all months and weeks.

|allCapital|containsNumOrHyphenOrDot|exclusionList|
|---|---|---|

This gave an F1 socre of 80.68.

![4](https://github.com/hming9527/Assignment6/blob/master/out/4.png)

### Fifth Feature Set
The last set of features was including conjuctions of features based on the previous feature set. Conjunctions I included were:

|prevTokenChunkTagNameTag|curTokenPOSChunkTag|nextTokenPOSChunkTag|conjunction of last feature set|
|---|---|---|---|

This gave an F1 score of 81.81.

![5](https://github.com/hming9527/Assignment6/blob/master/out/5.png)

## Usage
Compile all java programs, then run Nametagger.java.
