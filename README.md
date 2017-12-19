# [Jest](https://github.com/searchbox-io/Jest/tree/master/jest)
Jest is a Java HTTP Rest client for ElasticSearch. If you want to know about Jest, 
please read Jest source code.
https://github.com/searchbox-io/Jest

# [Rest Client Bulk operations](https://stackoverflow.com/questions/43339120/elasticsearch-bulk-insert-using-rest-client)
To improve performance, I want to send documents to ElasticSearch  in bulk instead sending one by one. 
I've read about elastic bulk API at https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-bulk.html

However, I am using Elasticsearch rest-client(https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html)
and couldn't find any example or documentation about how to make a bulk insert. 
All I could find was about bulk requests through transport client.

I guess I have to prepare request body as described here(https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html)
and pass it to restclient's performRequest method? Is there another way, for instance, a builder mechanism in the ES java rest-client library, 
to make bulk insert using rest?

