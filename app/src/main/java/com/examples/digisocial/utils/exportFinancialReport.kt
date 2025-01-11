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

fun exportFinancialReport(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
    val db = FirebaseFirestore.getInstance()

    db.collection("transactions").get()
        .addOnSuccessListener { querySnapshot ->
            val docsFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "relatório_financeiro")
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
            val text = "Relatório Financeiro\n\n"
            canvas.drawText(text, 50f, yPosition, paint)
            yPosition += 20f

            paint.textSize = 5f
            try {
                for (documentSnapshot in querySnapshot.documents) {
                    val tipo = documentSnapshot.getString("type") ?: "Desconhecido"
                    val descricao = documentSnapshot.getString("description") ?: "Sem descrição"
                    val valor = documentSnapshot.getDouble("amount") ?: 0.0

                    canvas.drawText("Tipo: $tipo", 50f, yPosition, paint)
                    yPosition += 15f
                    canvas.drawText("Descrição: $descricao", 50f, yPosition, paint)
                    yPosition += 15f
                    canvas.drawText("Valor: $valor €", 50f, yPosition, paint)
                    yPosition += 25f
                }

                pdfDocument.finishPage(page)

                val filePath = File(docsFolder, "relatorio_financeiro.pdf")
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
