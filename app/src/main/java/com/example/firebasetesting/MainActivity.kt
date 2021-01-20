package com.example.firebasetesting

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    val TAG="hitesh"
    lateinit var editTextFullName: EditText
    lateinit var nmbr: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextFullName = findViewById(R.id.name)
        nmbr = findViewById(R.id.phn)

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("FirebaseTesting").child("MyDb")

        val btn_enter : Button = findViewById(R.id.button)
        val btn_getData : Button = findViewById(R.id.button2)

        btn_enter.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val name = editTextFullName.text.toString()
                val phn = nmbr.text.toString()

                val user: HashMap<String, Any> = HashMap()
                user[phn] = name

                myRef.updateChildren(user)
                Toast.makeText(this@MainActivity, "Data Added successfully..", Toast.LENGTH_SHORT).show();
            }
        })

        btn_getData.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Toast.makeText(this@MainActivity, "Data Not found. Database Error.", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d(TAG, "***********************************************")
                            //Toast.makeText(ListPage.this,"Data found.",Toast.LENGTH_SHORT).show();
                            for (snapshot in dataSnapshot.children) {

                                Log.d(TAG,snapshot.key+ " " + snapshot.value)
                            }
                            Log.d(TAG, "***********************************************")


                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@MainActivity, databaseError.message, Toast.LENGTH_SHORT).show()
                    }
                })

            }
        })

    

    }
}