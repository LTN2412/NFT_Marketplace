package com.nftmarketplace.asset_elastic_search.repository;

import java.util.Set;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.nftmarketplace.asset_elastic_search.model.Asset;

public interface AssetRepository extends ElasticsearchRepository<Asset, String> {
  @Query("""
          {
          "fuzzy": {
            "name": {
              "value": "#{#keyword}",
              "fuzziness": "auto"
              }
          }
      }
      """)
  Set<Asset> findByNameFuzzy(String keyword);

  @Query("""
      {

      "bool": {
        "must": [
          {
            "wildcard": {
              "name": {
                "value": "#{#keyword}*"
              }
            }
          }
        ]
      }

      }
      """)
  Set<Asset> suggest(String keyword);
}
