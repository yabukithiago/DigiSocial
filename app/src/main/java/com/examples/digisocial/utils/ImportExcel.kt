package com.examples.digisocial.utils

import com.google.firebase.firestore.FirebaseFirestore
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream

fun importExcelToFirestore(filePath: String) {
    val db = FirebaseFirestore.getInstance()
    val file = File(filePath)

    if (!file.exists()) {
        return
    }

    FileInputStream(file).use { fis ->
        val workbook = WorkbookFactory.create(fis)
        val sheet = workbook.getSheetAt(0)
        val headerRow = sheet.getRow(0)

        for (i in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i)

            val dataMap = mutableMapOf<String, Any>()

            for (j in 0 until headerRow.physicalNumberOfCells) {
                val headerName = headerRow.getCell(j).toString()
                val cellValue = row.getCell(j)?.toString() ?: ""

                if (headerName == "numeroVisita") {
                    val numeroVisita = cellValue.toLongOrNull() ?: 0L  // Caso falhe, atribui 0L
                    dataMap[headerName] = numeroVisita
                } else {
                    dataMap[headerName] = cellValue
                }
            }

            dataMap.putIfAbsent("ownerId", "tânia")

            db.collection("beneficiary")
                .add(dataMap)
                .addOnCompleteListener { documentReference ->
                    val generatedId = documentReference.result.id

                    db.collection("beneficiary")
                        .document(generatedId)
                        .update("id", generatedId)
                        .addOnSuccessListener {
                        }
                }
                .addOnFailureListener {
                }
        }
        workbook.close()
    }
}
