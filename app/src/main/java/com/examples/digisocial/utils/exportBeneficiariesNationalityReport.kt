package com.examples.digisocial.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.google.firebase.firestore.FirebaseFirestore
import org.apache.poi.util.DocumentFormatException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun exportBeneficiariesNationalityReport(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
    val db = FirebaseFirestore.getInstance()

    db.collection("beneficiary").get()
        .addOnSuccessListener { querySnapshot ->
            val docsFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "relatorio_nacionalidades")
            if (!docsFolder.exists()) {
                docsFolder.mkdirs()
            }

            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            val paint = Paint()
            paint.textSize = 8f
            paint.isAntiAlias = true

            var yPosition = 50f
            val text = "Relatório de Nacionalidades dos Beneficiários\n\n"
            canvas.drawText(text, 50f, yPosition, paint)  // Ajuste a posição horizontal para 50f
            yPosition += 20f  // Atualiza a posição após o título

            paint.textSize = 5f  // Menor tamanho de fonte para os dados
            try {
                for (documentSnapshot in querySnapshot.documents) {
                    val nationality = documentSnapshot.getString("nacionalidade") ?: "Unknown"
                    canvas.drawText("Nacionalidade: $nationality", 50f, yPosition, paint)  // Ajuste a posição horizontal para 50f
                    yPosition += 20f  // Atualiza a posição para o próximo item
                }

                pdfDocument.finishPage(page)

                val filePath = File(docsFolder, "relatorio_nacionalidades.pdf")
                pdfDocument.writeTo(FileOutputStream(filePath))
                pdfDocument.close()

                onSuccess()

            } catch (e: DocumentFormatException) {
                e.printStackTrace()
                onFailure()
            } catch (e: IOException) {
                e.printStackTrace()
                onFailure()
            }
        }
        .addOnFailureListener { e ->
            e.printStackTrace()
            onFailure()
        }
}


