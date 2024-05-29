package com.example.aulathreadsecoroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulathreadsecoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var  pararThread = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnAbrirTela.setOnClickListener(){
            startActivity(
                Intent(this,MainActivity2::class.java)
            )

        }

        binding.btnIniciar.setOnClickListener(){
            //Thread(MinhaThread()).start()

            //Thread{}.start()

            //Thread(MinhaRunnable()).start()

            CoroutineScope(Dispatchers.IO).launch {

                repeat(15) { indice ->
                    Log.i("info_coroutine", "MinhaThread:$indice T: ${Thread.currentThread().name}")
                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "Executou"
                    }
                    delay(1000)

                }

            }

        }

        binding.btnAbrirParar.setOnClickListener(){
            pararThread = true
            binding.btnIniciar.text = "Reiniciar execucao"
            binding.btnIniciar.isEnabled = true
        }

    }

    inner class MinhaRunnable(): Runnable{
        override fun run() {
            repeat(15){ indice ->
                if (pararThread){
                    pararThread = false
                    return
                }
                Log.i("info_thread", "MinhaThread:$indice T: ${Thread.currentThread().name}")
                runOnUiThread{
                    binding.btnIniciar.text = "Executando:$indice T: ${Thread.currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if (indice == 14){
                        binding.btnIniciar.text = "Reiniciar execucao"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                Thread.sleep(1000)

            }
        }
    }

    inner class MinhaThread: Thread(){

        override fun run() {
            super.run()

            
        }
    }


}