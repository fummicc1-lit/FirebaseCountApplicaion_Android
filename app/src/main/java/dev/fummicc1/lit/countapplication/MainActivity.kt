package dev.fummicc1.lit.countapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

// Step1. カウントアプリで記録したいデータをまとめる
class Count {
    var number: Int = 0
}

class MainActivity : AppCompatActivity() {

    // Step2. Firestoreを操作できるインスタンスを作成
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Step2. カウントクラスのインスタンスを作成
    var count: Count = Count()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ボタンが押されたときにしたいことを書く
        plusButton.setOnClickListener {

            // Step3. カウントを1つ増やす

            // ここは普通のカウントアプリ
            count.number += 1
            numberTextView.text = count.number.toString()

            // Step4. 増えたカウントをFirestoreにアップロードする

            // Firestoreにデータをアップロードする
            firestore.collection("groups").document("group1").set(count)
        }

        // Step5. Firestoreから`number`をダウンロードする
        firestore.collection("groups").document("group1").addSnapshotListener { snapshot, error ->
            val data = snapshot?.data
            if (data != null) {
                // 取得できたカウントを変数に代入している
                count = snapshot.toObject(Count::class.java)!!
                // TextViewに最新のカウントを表示する
                numberTextView.text = count.number.toString()
            }
        }
    }
}