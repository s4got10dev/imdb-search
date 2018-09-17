USING PERIODIC COMMIT 1000
LOAD CSV WITH HEADERS FROM 'file:///title.principals.tsv.gz' AS row FIELDTERMINATOR '\t'
  MATCH (title:Title {titleId: row.tconst})
  MATCH (person:Person {personId: row.nconst})
  CREATE (person)-[:WORKED_IN {category: row.category}]->(title);
//Set 29613313 properties, created 29613313 relationships, completed after 925053 ms.


