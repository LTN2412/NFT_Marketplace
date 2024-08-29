package com.nftmarketplace.asset_elastic_service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.asset_elastic_service.model.Asset;

import reactor.core.publisher.Flux;

@Repository
public interface AssetRepository extends ReactiveElasticsearchRepository<Asset, String> {
  @Query("""
      {
        "match_all": {}
      }
      """)
  Flux<Asset> findAssetsPageable(Pageable pageable);

  @Query("""
      {
        "terms": {
          "author_id":{
            "index":"author",
            "path":"id",
            "id":"#{#id}"
          }
        }
      }
      """)
  Flux<Asset> findAllAssetsFrom1Author(String id, Pageable pageable);

  @Query("""
      {
        "match": {
          "tags": "#{#tagName}"
        }
      }
      """)
  Flux<Asset> findAllAssetsByTag(String tagName, Pageable pageable);

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
      }
      """)
  Flux<Asset> searchAssets(String field, String queryFuzzy, String queryWildcard, Integer limit);
}
