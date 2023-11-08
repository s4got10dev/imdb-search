:auto
USING PERIODIC COMMIT 100
LOAD CSV WITH HEADERS FROM 'file:///title.principals.tsv.gz' AS row FIELDTERMINATOR '\t'
  WITH row,
       CASE WHEN row.category='actor' OR row.category='actress' OR row.category='self' THEN row.tconst ELSE '' END AS t,
       CASE WHEN row.category='actor' OR row.category='actress' OR row.category='self' THEN row.nconst ELSE '' END AS n
  MATCH (person:Person {personId: n})
  MATCH (title:Title {titleId: t})
  CREATE (person)-[r:ACTED_IN {roles: split(replace(row.characters, '\\N', ''), ',')}]->(title);
//Set 17297832 properties, created 17297832 relationships, completed after 922098 ms.