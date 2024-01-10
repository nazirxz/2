package com.example.justicia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justicia.model.Message
import com.example.justicia.model.MessageAdapter
import com.example.justicia.model.Question

class ChatbotActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeTextView: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var messageList: MutableList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var questionDataList: ArrayList<Question>

    var mainQuestion: HashMap<String, String>? = null
    var subQuestion: MutableMap<String, Map<String, String>>? = null
    var answerSet: HashMap<String, String>? = null
    var pointer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_chatbot)
        messageList = ArrayList()
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        welcomeTextView = findViewById<TextView>(R.id.welcome_text)
        messageEditText = findViewById<EditText>(R.id.message_edit_text)
        sendButton = findViewById<ImageButton>(R.id.send_btn)

        //setup recycler view
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter
        val llm = LinearLayoutManager(this)
        llm.stackFromEnd = true
        recyclerView.setLayoutManager(llm)

        val back= findViewById<ImageView>(R.id.btnBack) as ImageView
        back.setOnClickListener {
            val dashboard = Intent(this,DashboardActivity::class.java)
            finish()
            startActivity(dashboard)
        }
        initQuestion()
        addResponse(firstQuestion)
        sendButton.setOnClickListener(View.OnClickListener { v: View? ->
            val question = messageEditText.getText().toString().trim { it <= ' ' }
            addToChat(question, Message.SENT_BY_ME)
            messageEditText.setText("")
            getResponse(question)
            welcomeTextView.setVisibility(View.GONE)
        })
    }

    fun initQuestion() {
        mainQuestion = HashMap()
        subQuestion = HashMap()
        answerSet = HashMap()
        val subquestionPerceraian: MutableMap<String, String> = HashMap()
        val subquestionHakTanah: MutableMap<String, String> = HashMap()
        val subquestionPelecehan: MutableMap<String, String> = HashMap()
        val subquestionKejahatan: MutableMap<String, String> = HashMap()

        mainQuestion!!["1"] = ". Perceraian"
        mainQuestion!!["2"] = ". Hak Tanah"
        mainQuestion!!["3"] = ". Pelecehan"
        mainQuestion!!["4"] = ". Kejahatan"

        subquestionPerceraian["11"] = ". Bagaimana proses penentuan hak asuh anak setelah perceraian?"
        subquestionPerceraian["12"] = ". Bagaimana pengadilan biasanya memutuskan pemisahan waktu antara orang tua?"
        subquestionPerceraian["13"] = ". Apakah mungkin untuk merundingkan perjanjian hak asuh tanpa melibatkan pengadilan?"
        subquestionPerceraian["14"] = ". Apa yang harus dilakukan jika salah satu orang tua ingin memodifikasi perintah hak asuh yang sudah ada?"
        subquestionPerceraian["15"] = ". Bagaimana jika salah satu orang tua tidak mematuhi perintah pengadilan terkait hak asuh atau dukungan anak?"
        (subQuestion as HashMap<String, Map<String, String>>)["1"] = subquestionPerceraian

        subquestionHakTanah["21"] = ". Dokumen apa yang diperlukan untuk pendaftaran tanah?"
        subquestionHakTanah["22"] = ". Bagaimana menentukan proporsi pembagian tanah di antara ahli waris?"
        subquestionHakTanah["23"] = ". Bagaimana menangani hutang atau beban finansial terkait dengan tanah dalam pembagian warisan?"
        subquestionHakTanah["24"] = ". Apa yang harus dilakukan jika salah satu ahli waris merasa mendapatkan hak yang tidak adil dalam pembagian tanah?"
        subquestionHakTanah["25"] = ". Apakah biaya yang terkait dengan pendaftaran tanah?"
        (subQuestion as HashMap<String, Map<String, String>>)["2"] = subquestionHakTanah

        subquestionPelecehan["31"] = ". Apa hak saya jika saya mengalami pelecehan di tempat kerja?"
        subquestionPelecehan["32"] = ". Bagaimana proses pemulihan dan tuntutan ganti rugi bekerja bagi korban pelecehan?"
        subquestionPelecehan["33"] = ". Apakah saya dapat menggugat seseorang yang melakukan pelecehan secara online?"
        subquestionPelecehan["34"] = ". Apakah saya bisa mendapatkan ganti rugi jika saya menjadi korban pelecehan?"
        subquestionPelecehan["35"] = ". Bagaimana saya dapat menghentikan pelecehan verbal ini dan melibatkan hukum dalam situasi ini?"
        (subQuestion as HashMap<String, Map<String, String>>)["3"] = subquestionPelecehan

        subquestionKejahatan["41"] = ". Apa yang harus dilakukan jika menjadi korban profiling yang banyak dialami masyarakat sekarang dan ditahan tanpa alasan yang jelas. Apa yang dapat saya lakukan untuk melindungi hak-hak saya?"
        subquestionKejahatan["42"] = ". Apa yang harus saya lakukan ketika menjadi korban pencurian?"
        subquestionKejahatan["43"] = ". Apa yang harus saya lakukan untuk melaporkan tindakan pembunuhan ketika saya tidak mempunyai bukti apapun?"
        subquestionKejahatan["44"] = ". Apa yang harus saya lakukan jika saya mendapatkan kekerasan domestik?"
        subquestionKejahatan["45"] = ". Bagaimana cara melaporkan oknum yang sudah melakukan pencemaran lingkungan?"
        (subQuestion as HashMap<String, Map<String, String>>)["4"] = subquestionKejahatan


        answerSet!!["11"] = "Prosedur dapat bervariasi, tetapi umumnya melibatkan pengajuan permohonan kepada pengadilan dan pertimbangan terhadap kesejahteraan anak."
        answerSet!!["12"] = "Pengadilan dapat mempertimbangkan faktor-faktor seperti hubungan anak dengan masing-masing orang tua, kemampuan orang tua untuk memberikan perawatan, dan kestabilan lingkungan."
        answerSet!!["13"] = "Ya, mediasi atau negosiasi dapat digunakan untuk mencapai kesepakatan tanpa melibatkan pengadilan. Ini dapat memberi kedua orang tua lebih banyak kendali atas hasilnya."
        answerSet!!["14"] = "Biasanya, pengajuan permohonan ke pengadilan diperlukan untuk merubah perintah hak asuh. Alasan yang kuat dan perubahan keadaan mungkin diperlukan."
        answerSet!!["15"] = "Langkah-langkah hukum dapat diambil, seperti mengajukan tuntutan perdata atau meminta pengadilan memberikan sanksi terhadap pelanggaran perintah."

        answerSet!!["21"] = "Dokumen yang mungkin diperlukan termasuk sertifikat tanah sebelumnya (jika ada), dokumen identitas, surat-surat kepemilikan sebelumnya, dan dokumen lain yang dapat memverifikasi kepemilikan."
        answerSet!!["22"] = "Pembagian umumnya dilakukan berdasarkan hukum warisan yang berlaku, yang dapat memperhitungkan jumlah ahli waris, hubungan keluarga, dan peraturan hukum setempat."
        answerSet!!["23"] = "Hutang atau beban finansial terkait tanah dapat diakomodasi dalam pembagian, dan ahli waris mungkin perlu bersepakat tentang cara menanggung atau melunasi kewajiban tersebut."
        answerSet!!["24"] = "Mereka dapat mengajukan gugatan atau mengekspresikan keberatan mereka kepada pengadilan, dan proses hukum dapat ditempuh untuk menyelesaikan perselisihan tersebut."
        answerSet!!["25"] = "Biaya pendaftaran tanah melibatkan biaya administratif, pajak pendaftaran, dan mungkin biaya notaris. Besaran biaya bisa berbeda-beda tergantung pada lokasi dan nilai properti."

        answerSet!!["31"] = "Jika Anda mengalami pelecehan di tempat kerja, Anda memiliki hak untuk melaporkannya. Proses ini dapat melibatkan pengajuan laporan ke manajemen, pengaduan ke departemen sumber daya manusia, atau bahkan melibatkan pihak berwenang jika perlu. Advokat dapat membantu Anda memahami hak-hak Anda, prosedur pelaporan, dan tindakan hukum yang dapat diambil."
        answerSet!!["32"] = "Proses pemulihan dan tuntutan ganti rugi melibatkan pengajuan klaim kepada pengadilan untuk mendapatkan kompensasi atas kerugian yang dialami oleh korban. Ini dapat mencakup biaya medis, kerugian ekonomi, dan kerugian lainnya. Advokat akan membantu menyusun tuntutan dan memastikan bahwa hak-hak korban terpenuhi."
        answerSet!!["33"] = "Ya, Anda dapat mengambil tindakan hukum terhadap seseorang yang melakukan pelecehan secara online. Pertama, simpan bukti elektronik seperti tangkapan layar, pesan teks, atau email yang mencatat pelecehan tersebut. Laporkan insiden ini kepada penyedia platform atau layanan yang digunakan oleh pelaku. Jika pelecehan tersebut melibatkan ancaman atau pelanggaran serius, segera hubungi advokat untuk menentukan opsi hukum yang tersedia, seperti mengajukan laporan ke pihak berwenang atau menggugat pelaku secara perdata."
        answerSet!!["34"] = "Mereka dapat mengajukan gugatan atau mengekspresikan keberatan mereka kepada pengadilan, dan proses hukum dapat ditempuh untuk menyelesaikan perselisihan tersebut."
        answerSet!!["35"] = "Biaya pendaftaran tanah melibatkan biaya administratif, pajak pendaftaran, dan mungkin biaya notaris. Besaran biaya bisa berbeda-beda tergantung pada lokasi dan nilai properti."

        answerSet!!["41"] = "Tetap tenang jika mendapati situasi seperti ini, ingatlah bahwa Anda memiliki hak untuk tetap bungkam dan memiliki hak untuk memiliki pengacara. Pengacara dapat membimbing Anda melalui proses hukum, membantu melindungi hak-hak Anda, dan mengevaluasi apakah ada dasar hukum untuk menantang profiling yang mungkin terjadi. Kumpulkan bukti yang mendukung klaim Anda. Ini bisa mencakup catatan kejadian, pesan teks, foto, atau rekaman lain. Jika Anda merasa bahwa profiling yang Anda alami melibatkan pelanggaran hukum atau melanggar hak-hak Anda, pertimbangkan untuk melaporkannya kepada otoritas pengawas, lembaga hak asasi manusia, atau organisasi-organisasi yang berkaitan."
        answerSet!!["42"] = "Pastikan keselamatan anda, segera pindahkan diri ke lokasi yang aman. Hindari menyentuh atau merusak tempat kejadian atau  barang bukti potensial. Jika Anda memiliki asuransi yang mencakup pencurian, hubungi perusahaan asuransi Anda segera setelah melaporkan kejadian ke polisi. Berikan informasi yang diperlukan untuk memulai proses klaim. Jika kunci, kartu kredit, atau dokumen identitas dicuri, pertimbangkan untuk mengganti kunci, memblokir kartu kredit, dan melaporkan kehilangan identitas kepada pihak berwenang yang bersangkutan. Jika pelaku berhasil ditangkap dan kasus berlanjut ke persidangan, bersiaplah untuk memberikan kesaksian jika diperlukan. Pengacara atau penyidik mungkin membutuhkan keterangan Anda sebagai saksi."
        answerSet!!["43"] = "YSegera hubungi pihak berwenang, laporkan segala sesuatu yang Anda tahu atau dengar, bahkan jika tidak memiliki bukti fisik. Jelaskan secara rinci apa yang Anda ketahui atau dengar, termasuk deskripsi orang atau kendaraan yang terlibat, waktu dan tempat kejadian, serta segala hal lain yang mungkin relevan. Jika Anda khawatir tentang keselamatan pribadi Anda atau ingin melaporkan secara anonim, tanyakan kepada petugas apakah ada mekanisme anonim yang dapat Anda gunakan. Jika penyelidikan dimulai, bekerjasamalah sepenuhnya dengan pihak berwenang. Mereka dapat memberikan panduan lebih lanjut dan memberi tahu Anda tentang perkembangan penyelidikan. Penting untuk tidak mencoba melakukan penyelidikan sendiri. Biarkan pihak berwenang yang berpengalaman menangani kasus ini. Tindakan sendiri dapat membahayakan Anda dan mempersulit proses penyelidikan."
        answerSet!!["44"] = "Segera hubungi pihak berwenang, laporkan segala sesuatu yang terjadi, yang bahkan jika tidak memiliki bukti fisik. Jelaskan secara rinci apa yang Anda ketahui atau dengar, termasuk deskripsi orang atau kendaraan yang terlibat, waktu dan tempat kejadian, serta segala hal lain yang mungkin relevan. Jika Anda khawatir tentang keselamatan pribadi Anda atau ingin melaporkan secara anonim, tanyakan kepada petugas apakah ada mekanisme anonim yang dapat Anda gunakan. Jangan setuju untuk menyelesaikan masalah ini tanpa dukungan hukum. Pengacara kami dapat membantu memastikan bahwa hak-hak Anda dihormati dan memberikan perlindungan yang tepat."
        answerSet!!["45"] = "Catat dengan detail kejadian pencemaran lingkungan yang Anda saksikan. Catat tanggal, waktu, dan lokasi kejadian, serta deskripsi lengkap tentang apa yang terjadi. Kumpulkan bukti fisik atau digital yang mendukung klaim pencemaran lingkungan. Ini bisa berupa foto, video, sampel air atau tanah, atau catatan yang mencerminkan dampak pencemaran. Catat informasi yang dapat membantu mengidentifikasi pelaku, seperti nama perusahaan, alamat, atau nomor kontak. Laporkan pencemaran lingkungan kepada otoritas lingkungan setempat. Biasanya, Badan Lingkungan Hidup (BLH) atau instansi pemerintah setempat yang bertanggung jawab atas lingkungan dapat menjadi tempat pelaporan."

    }

    val firstQuestion: String
        get() {
            var question = "Mau Konsultasi Apa Hari Ini? :)" +
                    " Pilih Kategori !"
            for ((key, value) in mainQuestion!!) {
                question += "\n$key $value"
            }
            return question
        }

    fun getMainQuestion(question: String): String? {
        return mainQuestion!![question]
    }

    fun getSubQuestion(mainQuestion: String): String {
        var question = """Topik : ${getMainQuestion(mainQuestion)}""".trimIndent()
        val subQuestions = subQuestion!![mainQuestion]!!

        for ((key, value) in subQuestions!!) {
            question += "\n$key $value"
        }
        question += "\n0. Menu Utama"
        return question
    }

    fun getAnswer(subQuestion: String): String? {
        var answer = answerSet!!.get(subQuestion)
        answer += "\n\n0. Menu Utama"
        return answer
    }

    fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messageList!!.add(Message(message, sentBy))
            messageAdapter!!.notifyDataSetChanged()
            recyclerView!!.smoothScrollToPosition(messageAdapter!!.itemCount)
        }
    }

    fun addResponse(response: String) {
//        if (!messageList!!.isEmpty()) messageList!!.removeAt(messageList!!.size - 1)
        addToChat(response, Message.SENT_BY_BOT)
    }

    fun getResponse(question: String) {
        when (pointer) {
            0 -> {
                addResponse(getSubQuestion(question))
                pointer++
            }

            1 -> if (question == "0") {
                addResponse(firstQuestion)
                pointer = 0
            } else {
                getAnswer(question)?.let { addResponse(it) }
                pointer++
            }

            2 -> {
                addResponse(firstQuestion)
                pointer = 0
            }

            else -> {}
        }
    }
}