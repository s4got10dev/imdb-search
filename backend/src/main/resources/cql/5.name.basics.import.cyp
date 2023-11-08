:auto
USING PERIODIC COMMIT 100
LOAD CSV WITH HEADERS FROM 'file:///name.basics.tsv.gz' AS row FIELDTERMINATOR '\t'
  CREATE (person:Person {personId: row.nconst, name: row.primaryName});
//Added 8794156 labels, created 8794156 nodes, set 17588312 properties, completed after 230294 ms.