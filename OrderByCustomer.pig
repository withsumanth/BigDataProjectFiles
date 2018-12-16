amazon = LOAD '/AmazonDataSet/combinedDataset.tsv' USING PigStorage('\t') as (marketplace,customer_id,review_id,product_id,product_parent,product_title,product_category,star_rating,helpful_votes,total_votes,vine,	verified_purchase,review_headline,review_body,review_date);
amazon = FILTER amazon BY review_headline == 'Five Stars';
top10 = LIMIT orderedData 10;
amazon_all = FOREACH orderedData GENERATE marketplace,customer_id,review_id,product_id,product_parent,product_title,product_category,total_votes,review_headline;
STORE amazon_all INTO '/PigOutput';
