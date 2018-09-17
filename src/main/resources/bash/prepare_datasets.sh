#!/usr/bin/env bash
if [ -z $IMDB_DATASETS ]; then
    echo "Please set IMDB_DATASET folder that will be used for downloading and preparing IMDb datasets for import"
    exit 1
fi

cd $IMDB_DATASETS
wget https://datasets.imdbws.com/title.basics.tsv.gz
wget https://datasets.imdbws.com/title.ratings.tsv.gz
wget https://datasets.imdbws.com/name.basics.tsv.gz
wget https://datasets.imdbws.com/title.principals.tsv.gz

gunzip title.basics.tsv.gz
sed -i "s/\"/'/g" title.basics.tsv
gzip title.basics.tsv

gunzip name.basics.tsv.gz
sed -i "s/\"/'/g" name.basics.tsv
gzip name.basics.tsv

gunzip title.principals.tsv.gz
sed -i "s/\"/'/g; s/\[\]//g" title.principals.tsv
gzip title.principals.tsv


