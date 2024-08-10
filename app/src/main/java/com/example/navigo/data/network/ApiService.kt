package com.example.navigo.data.network

object ApiService {
    val searchService: SearchService by lazy {
        RetrofitClientInstance.retrofitInstance.create(SearchService::class.java)
    }
    val reverseService: ReverseService by lazy {
        RetrofitClientInstance.retrofitInstance.create(ReverseService::class.java)
    }
    val directionService: DirectionService by lazy {
        RetrofitClientInstance.retrofitInstance.create(DirectionService::class.java)
    }
    val distanceMatrixService: DistanceMatrixService by lazy {
        RetrofitClientInstance.retrofitInstance.create(DistanceMatrixService::class.java)
    }
    val tspService: TSPService by lazy {
        RetrofitClientInstance.retrofitInstance.create(TSPService::class.java)
    }
    val mapMatchingService: MapMatchingService by lazy {
        RetrofitClientInstance.retrofitInstance.create(MapMatchingService::class.java)
    }
    val geocodingService: GeocodingService by lazy {
        RetrofitClientInstance.retrofitInstance.create(GeocodingService::class.java)
    }
}
