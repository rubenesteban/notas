package com.notas.inventory

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.notas.inventory.data.Item
import com.notas.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDateTime


class UnionViewModel(itemsRepository: ItemsRepository) : ViewModel() {

    val contracts = LocalContext

    //var listaInf: ArrayList<Informacion> = arrayListOf()

    private val TAG: String = "Reebook"

    val pdfUiState: StateFlow<PdfUiState> =
        itemsRepository.getAllItemsStream().map { PdfUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PdfUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    private var Work: List<Item> = listOf<Item>()
    private var Milk: List<Item> = listOf<Item>()
    private var yun: MutableList<String> = mutableListOf<String>()
    private var eli: MutableSet<String> = mutableSetOf<String>("")
    var job: List<String> = listOf<String>()
    private var tin = itemsRepository.getAllItemsStream()


    suspend fun checkFlow(f:Flow<List<Item>>): List<Item> {

        val elo = f.collect{ _ -> Work}
        val mu = Work

        return mu
    }



    fun listare(a: MutableList<Item>): List<Item> {

       val ls = a.asSequence().toList()
        return ls
    }

    var can by mutableStateOf("")
        private set

    fun checkList(): ArrayList<Item>  {
        viewModelScope.launch {
            Work = pdfUiState.value.itemList as MutableList<Item>
            var yup = checkFlow(tin)
            Milk = yup

        }
        val elo = Milk
        val mu = elo as ArrayList<Item>
        return mu
    }


    fun golf(r: List<Double>): Double {
        var mil:Double=0.0
        for (i in r.indices) {

            mil += r[i]

        }
        return mil
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun hora(): LocalDateTime? {
        val datelime = LocalDateTime.now()
        return datelime
    }





    // var elo = pdfUiState.value.itemList

  //  var listaUsuarios1: ArrayList<Item> =  elo as ArrayList<Item>
   // var listaUsuarios1: ArrayList<Item> =  arrayListOf()


    @RequiresApi(Build.VERSION_CODES.O)
    fun crearPDF(u: List<String>, r: List<Double>, p: List<Int>) {
        try {
            val carpeta = "/file-pdf"
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + carpeta

            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
                //Toast.makeText(context, "CARPETA CREADA", Toast.LENGTH_SHORT).show()
            }
            val h = hora()
            val file = File(dir, "pdf$h.pdf")
            val fileOutputStream = FileOutputStream(file)

            val mas = golf(r).toString()

            val listUsurious1: Flow<List<Item>> =  tin

           // val listUsurious1: Flow<List<Item>> =  tin




            val documento = Document()
            PdfWriter.getInstance(documento, fileOutputStream)

            documento.open()

            val titulo3 = Paragraph(
                "\t\t\t                              Nota de Venta \n\n\n",
                FontFactory.getFont("arial", 22f, Font.BOLD, BaseColor.BLUE)
            )

            val titulo2 = Paragraph(
                "\t\t\t    Detalles \n\n",
                FontFactory.getFont("arial", 22f, Font.BOLD, BaseColor.BLUE)

            )


            val titulo1 = Paragraph(
                "Lista de Compras \n\n",
                FontFactory.getFont("arial", 22f, Font.BOLD, BaseColor.BLUE)
            )

            //val emo = checkList()


            //val mik = emo.size.toString()
           // var listaUsuarios1: ArrayList<Item> = emo


            documento.add(titulo3)


            val tabla1 = PdfPTable(3)
            tabla1.addCell("UNIT")
            tabla1.addCell("PRODUCT")
            tabla1.addCell("PRICE")



            for (i in u.indices) {
                tabla1.addCell(p[i].toString())
                tabla1.addCell(u[i])
                tabla1.addCell(r[i].toString())

            }

            tabla1.addCell("")
            tabla1.addCell("SubTotal =")
            tabla1.addCell(mas)


            val tabla2 = PdfPTable(3)
            tabla2.addCell(h.toString())
            tabla2.addCell("Find here:")
            tabla2.addCell("https://n9.cl/abeja77")

            documento.add(titulo1)

            documento.add(tabla1)

            documento.add(titulo2)


            documento.add(tabla2)

            documento.close()


        } catch (e: FileNotFoundException) {
            e.printStackTrace();
        } catch (e: DocumentException) {
            e.printStackTrace()
        }
    }




}


data class PdfUiState(val itemList: List<Item> = listOf())