package com.nftmarketplace.asset_elastic_search.repository;

import java.util.Set;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.nftmarketplace.asset_elastic_search.model.Asset;

public interface AssetRepository extends ElasticsearchRepository<Asset, String> {
  // @Query("""
  // {
  // "fuzzy": {
  // "name": {
  // "value": "#{#keyword}",
  // "fuzziness": "auto"
  // }
  // }
  // }
  // """)
  // Set<Asset> findByNameFuzzy(String keyword);

  // @Query("""
  // {
  // "bool": {
  // "must": [
  // {
  // "wildcard": {
  // "name": {
  // "value": "#{#keyword}*"
  // }
  // }
  // }
  // ]
  // }
  // }
  // """)
  // Set<Asset> suggest(String keyword);

  // @Query("""

  // """)
  // Set<Asset> findAllAssetsPageable(int offset, int limit);

  @Query("""
      {
        "terms": {
          "author_id":{
            "index":"author",
            "path":"id",
            "id":"#{#id}"
          }
        }
      },"size":#{#limit}
      """)
  Set<Asset> findAllAssetsFrom1Author(String id, int limit);

  @Query("""
      {
        "term": {
          "tags": {
            "value": "#{#name}"
          }
        }
      },"size": #{#limit}
      """)
  Set<Asset> findAllAssetsByTag(String name, int limit);

  @Query("""
      {
        "bool": {
          "should": [
            {
              "match": {
                "#{#field}": {
                  "query": "#{#queryFuzzy}",
                  "fuzziness": "AUTO"
                }
              }
            },
            {
              "wildcard": {
                "#{#field}": {
                  "value": "#{#queryWildcard}*"
                }
              }
            }
          ]
        }
      },"size":#{#limit}
      ,"_source": ["#{#field}"]
          """)
  Set<Asset> searchAssets(String field, String queryFuzzy, String queryWildcard, int limit);
}