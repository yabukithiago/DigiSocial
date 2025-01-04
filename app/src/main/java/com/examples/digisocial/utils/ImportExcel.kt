package com.examples.digisocial.utils

import com.google.firebase.firestore.FirebaseFirestore
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream

fun importExcelToFirestore(filePath: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val file = File(filePath)


    if (!file.exists()) {
        onFailure()
        return
    }

    FileInputStream(file).use { fis ->
        val workbook = WorkbookFactory.create(fis)
        val sheet = workbook.getSheetAt(0)
        val headerRow = sheet.getRow(0)
        val totalRows = sheet.physicalNumberOfRows - 1
        var successCount = 0
        var failureOccurred = false

        for (i in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i)

            val dataMap = mutableMapOf<String, Any>()

            for (j in 0 until headerRow.physicalNumberOfCells) {
                val headerName = headerRow.getCell(j).toString()
                val cellValue = row.getCell(j)?.toString() ?: ""

                if (headerName == "numeroVisita") {
                    val numeroVisita = cellValue.toLongOrNull() ?: 0L
                    dataMap[headerName] = numeroVisita
                } else {
                    dataMap[headerName] = cellValue
                }
            }

            dataMap.putIfAbsent("ownerId", "tânia")

            db.collection("beneficiary")
                .add(dataMap)
                .addOnCompleteListener { documentReference ->
                    if (documentReference.isSuccessful) {
                        val generatedId = documentReference.result.id
                        db.collection("beneficiary")
                            .document(generatedId)
                            .update("id", generatedId)
                            .addOnSuccessListener {
                                successCount++
                                if (successCount == totalRows && !failureOccurred) {
                                    onSuccess()
                                }
                            }
                            .addOnFailureListener {
                                failureOccurred = true
                                onFailure()
                            }
                    } else {
                        failureOccurred = true
                        onFailure()
                    }
                }
        }
        workbook.close()
    }
}