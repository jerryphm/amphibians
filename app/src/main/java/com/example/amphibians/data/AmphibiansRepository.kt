package com.example.amphibians.data

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class NetworkAmphibiansRepository() : AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> {
        return AmphibiansApi.retrofitService.getAmphibians()
    }
}