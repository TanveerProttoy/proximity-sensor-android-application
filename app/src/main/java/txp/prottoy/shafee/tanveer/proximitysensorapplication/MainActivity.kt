package txp.prottoy.shafee.tanveer.proximitysensorapplication

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var proximitySensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if(proximitySensor == null) {
            Toast.makeText(applicationContext, "No proximity sensor detected", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val view: View = window.decorView
        val value: Float = sensorEvent.values[0]
        if(value < proximitySensor.maximumRange) {
            setTextValue(value.toString())
            view.setBackgroundColor(Color.RED)
        }
        else {
            setTextValue(value.toString())
            view.setBackgroundColor(Color.GREEN)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun setTextValue(value: String) {
        mainTextView.text = value
    }
}
