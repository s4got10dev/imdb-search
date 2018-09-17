USING PERIODIC COMMIT 1000
LOAD CSV WITH HEADERS FROM 'file:///title.basics.tsv.gz' AS row FIELDTERMINATOR '\t'
  CREATE (title:Title {titleId: row.tconst, titleType: row.titleType, primaryTitle: row.primaryTitle, originalTitle: row.originalTitle})
  WITH title, split(row.genres, ',') AS genres
  UNWIND genres AS g
  MERGE (genre:Genre {name: g})
  CREATE (title)-[:HAS_GENRE]->(genre);
//Added 5217937 labels, created 5217937 nodes, set 15653753 properties, created 8368380 relationships, completed after 252119 ms.