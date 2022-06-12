package com.example.firestoredatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnWrite.setOnClickListener { // 쓰기 버튼
            val aPlayerId = etId.text // player id
            val aPlayerNickname = etNickname.text // player nickname

            val aplayerdata = hashMapOf( // 저장할 필드값들
                "nickname" to aPlayerNickname.toString() // 필드가 1개 이상일 땐, 붙인 다음 똑같이 필드를 만들면 됨
            // 값은 int float 등등 가능
            )
            db.collection("player").document(aPlayerId.toString()) // 첫번째칸 컬렉션 player id 저장 부분
                .set(aplayerdata) // 필드 데이터 설정
                .addOnSuccessListener { // 쓰기 성공했을 경우
                    textView1.text = "write succeed : $aPlayerId"
                    etId.text.clear()
                    etNickname.text.clear()
                }
                .addOnFailureListener { // 쓰기 실패했을 경우
                    textView1.text = "write fail"
                    etId.text.clear()
                    etNickname.text.clear()
                }
        }
        btnRead.setOnClickListener { // 읽기 버튼
        val aPlayerId = etId.text
            db.collection("player") // 첫번째칸 컬렉션
                .get()
                .addOnCompleteListener { task ->// 제대로 읽었다면
                    if (task.isSuccessful) {
                        for (i in task.result!!) {
                            if (i.id == aPlayerId.toString()) { // 입력한 데이터와 같은 이름이 있다면(player id 부분)
                                val theNickname = i.data["nickname"] // 필드 데이터
                                textView2.text = "read success : " + theNickname.toString()
                                break
                            }
                        }
                    } else { // 오류 발생시

                    }
                }
        }
    }
}