:auto
USING PERIODIC COMMIT 100
LOAD CSV WITH HEADERS FROM 'file:///title.ratings.tsv.gz' AS row FIELDTERMINATOR '\t'
  MERGE (title:Title {titleId: row.tconst})
    ON MATCH SET title.averageRating = toFloat(row.averageRating)
    ON MATCH SET title.numVotes = toInteger(row.numVotes);
//Set 1719290 properties, completed after 22491 ms.