package com.example.justicia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.BufferedReader
import java.io.InputStreamReader

class GrafikActivity : AppCompatActivity() {

    private lateinit var pieChart2: PieChart
    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_grafik)

        pieChart2 = findViewById(R.id.pieChart2)
        pieChart = findViewById(R.id.pieChart)
        barChart = findViewById(R.id.barChart)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        // Set bottom navigation selected item to bottom_grafik
        bottomNavigation.menu.findItem(R.id.bottom_grafik).isChecked = true

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val dashboard = Intent(this, DashboardActivity::class.java)
                    startActivity(dashboard)
                    true
                }
                R.id.bottom_grafik -> {
                    true
                }
                else -> false
            }
        }
        loadDataAndDisplayCharts()
    }

    private fun loadDataAndDisplayCharts() {
        val inputStream = resources.openRawResource(R.raw.cluster_results)
        val reader = BufferedReader(InputStreamReader(inputStream))

        val kategoriDeskripsiMap = mapOf(
            "HakTanah" to "Hak Tanah",
            "Perceraian" to "Perceraian",
            "Kejahatan" to "Kejahatan"
        )

        val namaBertanyaMap = HashMap<String, Int>()
        val kategoriPertanyaanMap = HashMap<String, Int>()
        val platformMap = HashMap<String, Int>()

        var line: String?
        while (reader.readLine().also { line = it } != null) {
            val parts = line!!.split(",".toRegex()).toTypedArray()
            val nama = parts[1]
            val kategoriPertanyaan = parts[2]
            val bertanya = parts[3]
            val platform = parts[4]

            val bertanyaCount = namaBertanyaMap[nama] ?: 0
            val bertanyaAsInt = bertanya.toIntOrNull() ?: 0
            namaBertanyaMap[nama] = bertanyaCount + bertanyaAsInt

            val kategoriCount = kategoriPertanyaanMap[kategoriPertanyaan] ?: 0
            kategoriPertanyaanMap[kategoriPertanyaan] = kategoriCount + 1

            // Gabungkan platform yang bukan 'Justicia' ke dalam kategori 'Platform Luar'
            val platformKey = if (platform != "Justicia") "Platform Luar" else platform
            val platformCount = platformMap[platformKey] ?: 0
            platformMap[platformKey] = platformCount + 1
        }

        val sortedNamaBertanya = namaBertanyaMap.toList().sortedByDescending { (_, value) -> value }.take(10).toMap()

        val namaEntries = ArrayList<PieEntry>()
        sortedNamaBertanya.forEach { (nama, total) ->
            namaEntries.add(PieEntry(total.toFloat(), nama))
        }

        val platformEntries = ArrayList<PieEntry>()

        val kategoriPertanyaanEntries = ArrayList<BarEntry>()
        val sortedKategoriPertanyaan = kategoriPertanyaanMap.toList().sortedByDescending { (_, value) -> value }.toMap()
        sortedKategoriPertanyaan.forEach { (kategori, total) ->
            val deskripsi = kategoriDeskripsiMap[kategori] ?: "Deskripsi Tidak Tersedia"
            kategoriPertanyaanEntries.add(BarEntry(kategoriPertanyaanEntries.size.toFloat(), total.toFloat(), deskripsi))
        }


        platformMap.forEach { (platform, total) ->
            platformEntries.add(PieEntry(total.toFloat(), platform))
        }

        preparePieChart(namaEntries, "Orang yang Sering Bertanya")
        prepareBarChart(kategoriPertanyaanEntries)
        preparePlatformPieChart(platformEntries)
    }

    private fun preparePieChart(entries: List<PieEntry>, chartLabel: String) {
        val dataSetPieChart = PieDataSet(entries, "User yang sering bertanya")
        dataSetPieChart.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val dataPieChart = PieData(dataSetPieChart)
        pieChart.data = dataPieChart
        pieChart.invalidate()
    }

    private fun prepareBarChart(entries: List<BarEntry>) {
        val barDataSet = BarDataSet(entries, "Kategori Pertanyaan yang Sering Ditanya")
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val barData = BarData(barDataSet)
        barChart.data = barData

        // Tambahkan kode di bawah ini untuk menampilkan deskripsi pada label kategori pertanyaan
        barDataSet.valueFormatter = object : ValueFormatter() {
            override fun getBarLabel(barEntry: BarEntry?): String {
                return barEntry?.data as? String ?: ""
            }
        }

        barChart.invalidate()
    }

    private fun preparePlatformPieChart(entries: List<PieEntry>) {
        val dataSetPlatformPieChart = PieDataSet(entries, "Platform yang Sering Digunakan")
        dataSetPlatformPieChart.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val dataPlatformPieChart = PieData(dataSetPlatformPieChart)
        pieChart2.data = dataPlatformPieChart
        pieChart2.invalidate()
    }
}