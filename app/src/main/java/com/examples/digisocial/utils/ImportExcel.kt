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

        // Para cada linha (exceto a primeira, que são os cabeçalhos)
        for (i in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i)

            // Cria um map para os dados da linha
            val dataMap = mutableMapOf<String, Any>()

            // Itera pelas colunas da linha
            for (j in 0 until headerRow.physicalNumberOfCells) {
                val headerName = headerRow.getCell(j).toString()

                // Obtém o valor da célula da linha (na coluna j)
                val cellValue = row.getCell(j)?.toString() ?: ""

                if (headerName == "numeroVisita") {
                    val numeroVisita = cellValue.toLongOrNull() ?: 0L  // Caso falhe, atribui 0L
                    dataMap[headerName] = numeroVisita
                } else {
                    // Para outras colunas, adiciona o valor como string
                    dataMap[headerName] = cellValue
                }
            }

            // Adiciona dados adicionais ao map
            dataMap.putIfAbsent("ownerId", "tânia")

            // Adiciona os dados ao Firestore
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
                    // Falha
                }
        }
        workbook.close()
    }
}
