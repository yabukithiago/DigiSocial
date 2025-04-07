package com.examples.digisocial.di

import com.examples.digisocial.data.repository.BeneficiaryRepositoryImpl
import com.examples.digisocial.data.repository.VisitRepositoryImpl
import com.examples.digisocial.domain.repository.BeneficiaryRepository
import com.examples.digisocial.domain.repository.VisitRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

const val BENEFICIARY = "beneficiary"
const val SCHEDULE = "schedule"
const val TRANSACTION = "transactions"
const val USER = "users"
const val VISIT = "visits"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    @Named(BENEFICIARY)
    fun provideBeneficiaryRef(): CollectionReference {
        return Firebase.firestore.collection(BENEFICIARY)
    }

    @Provides
    @Singleton
    @Named(SCHEDULE)
    fun provideScheduleRef(): CollectionReference {
        return Firebase.firestore.collection(SCHEDULE)
    }

    @Provides
    @Singleton
    @Named(TRANSACTION)
    fun provideTransactionRef(): CollectionReference {
        return Firebase.firestore.collection(TRANSACTION)
    }

    @Provides
    @Singleton
    @Named(USER)
    fun provideUserRef(): CollectionReference {
        return Firebase.firestore.collection(USER)
    }

    @Provides
    @Singleton
    @Named(VISIT)
    fun provideVisitRef(): CollectionReference {
        return Firebase.firestore.collection(VISIT)
    }

    @Provides
    @Singleton
    fun provideBeneficiaryRepository(@Named(BENEFICIARY) beneficiaryRef: CollectionReference): BeneficiaryRepository {
        return BeneficiaryRepositoryImpl(beneficiaryRef)
    }

    //    @Provides
//    @Singleton
//    fun provideScheduleRepository(@Named(SCHEDULE) scheduleRef: CollectionReference): ScheduleRepository {
//        return ScheduleRepositoryImpl(scheduleRef)
//    }
//
//    @Provides
//    @Singleton
//    fun provideTransactionRepository(@Named(TRANSACTION) transactionRef: CollectionReference): TransactionRepository {
//        return TransactionRepositoryImpl(transactionRef)
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserRepository(@Named(USER) userRef: CollectionReference): UserRepository {
//        return UserRepositoryImpl(userRef)
//    }
    @Provides
    @Singleton
    fun provideVisitRepository(@Named(VISIT) visitRef: CollectionReference): VisitRepository {
        return VisitRepositoryImpl(visitRef)
    }
}