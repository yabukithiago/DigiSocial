package com.examples.digisocial.domain.repository

import com.examples.digisocial.domain.models.Beneficiary
import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Visit
import kotlinx.coroutines.flow.Flow

typealias BeneficiaryResponse = Response<Beneficiary>
typealias BeneficiaryListResponse = Response<List<Beneficiary>>
typealias AddBeneficiaryResponse = Response<String>
typealias UpdateBeneficiaryResponse = Response<Void>
typealias DeleteBeneficiaryResponse = Response<Void>
typealias AddVisitBeneficiaryResponse = Response<String>

interface BeneficiaryRepository {

    fun getBeneficiary(): Flow<BeneficiaryListResponse>

    suspend fun getBeneficiaryById(beneficiaryId: String): BeneficiaryResponse

    suspend fun addBeneficiary(beneficiary: Beneficiary): AddBeneficiaryResponse

    suspend fun updateBeneficiary(beneficiary: Beneficiary): UpdateBeneficiaryResponse

    suspend fun deleteBeneficiary(id: String): DeleteBeneficiaryResponse

    suspend fun addVisitToBeneficiary(beneficiaryId: String, visit: Visit): AddVisitBeneficiaryResponse
}