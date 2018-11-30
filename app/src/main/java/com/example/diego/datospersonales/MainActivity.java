package com.example.diego.datospersonales;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    String nombre;
    String apellidos;
    String edad;
    String mayorEdad;
    String genero;
    String estadoCivil;
    String hijos;
    String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()//Llamamos a la libreria
                .setDefaultFontPath("font/clarendom.ttf") //Fuente agregada en los assets dentro de la carpeta fonts
                .setFontAttrId(R.attr.fontPath)
                .build());




        //Creamos todos los editText para recoger su información
        final EditText dameNombre = (EditText) findViewById(R.id.etNombre);
        final EditText dameApellidos = (EditText) findViewById(R.id.etApellidos);
        final EditText dameEdad = (EditText) findViewById(R.id.etEdad);

        //Creamos los radiobutton para recoger la infomación
        final RadioButton radioHombre = (RadioButton) findViewById(R.id.rbHombre);
        final RadioButton radioMujer = (RadioButton) findViewById(R.id.rbMujer);

        //Creamos el elemento switch para recoger la información
        final Switch dameHijos = (Switch) findViewById(R.id.switchHijos);

        //Creamos el elemento textView para mostrar la información
        final TextView dameResultado = (TextView) findViewById(R.id.textView9);

        //Creamos el elemento spinner para pasarle los datos
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //Recibo los strings para usarlos en el spinner
        String casado = getResources().getString(R.string.casado);
        String separado = getResources().getString(R.string.separado);
        String viudo = getResources().getString(R.string.viudo);
        String otro = getResources().getString(R.string.otro);

        String[] estado = {casado,separado,viudo,otro};
        //Pasamos los elementos a la lista desplegable
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estado));

        //Creamos el elemento boton y le creamos el listener
        Button botonGuardar = (Button)findViewById(R.id.buttonGuardar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    //Recogemos la información y la guaradmos en las variables
                    nombre = dameNombre.getText().toString();
                    apellidos = dameApellidos.getText().toString();
                    edad = dameEdad.getText().toString();

                    //Pasamos el dato recibido a int para poder hacer la condición
                    int edadFinal = Integer.parseInt(edad);

                    //Condición que nos dira si es menor o mayor de edad
                    if (edadFinal >= 18) {
                        mayorEdad = getResources().getString(R.string.mayorEdad);
                    } else {
                        mayorEdad = getResources().getString(R.string.menorEdad);
                    }


                    //Depende de la opción seleccionada será hombre o mujer
                    if (radioHombre.isChecked()) {
                        genero = getResources().getString(R.string.masculino);
                    } else {
                        genero = getResources().getString(R.string.femenino);
                    }

                    //Recogemos los datos seleccionados en el spinner y lo guardamos en la variable
                    estadoCivil = spinner.getSelectedItem().toString();


                    //Depende de si se ha marcado o no mostrara si tiene hijos o no
                    if (dameHijos.isChecked()) {
                        hijos = getResources().getString(R.string.conHijos);
                    } else {
                        hijos = getResources().getString(R.string.sinHijos);
                    }


                    //Si nombre esta vacio se muestra un mensaje
                    if (nombre.isEmpty()) {
                        //Mostramos el texto en color rojo
                        PonerColores();
                        resultado = getResources().getString(R.string.errorNombre);
                        dameResultado.setText(resultado);
                    //Si apellidos esta vacio se muestra un mensaje
                    } else if (apellidos.isEmpty()) {
                        //Mostramos el texto en color rojo
                        PonerColores();
                        resultado = getResources().getString(R.string.errorApellidos);
                        dameResultado.setText(resultado);
                    }else {
                        //Guardamos todos los resultados en una variable para mostrarla
                        resultado = nombre + " " + apellidos + "," + mayorEdad + "," + genero + "," + estadoCivil + "," + hijos;
                        //Mostramos el resultado en el campo de texto
                        dameResultado.setText(resultado);
                    }

                //Recogemos la exepción que nos da error por no introducir nada en el campo de texto edad, que es int
                }catch (NumberFormatException e){
                    final TextView dameResultado = (TextView) findViewById(R.id.textView9);
                    //Mostramos el texto en color rojo
                    PonerColores();
                    //Mostramos en el resultado que no ha introducido la edad
                    resultado = getResources().getString(R.string.errorEdad);
                    dameResultado.setText(resultado);
                }
            }

        });

        //Creamos el boton reser y le creamos el listener
        Button botonReset = (Button)findViewById(R.id.buttonReset);
        botonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                //Volvemos todos los campos de texto vacios
                dameNombre.setText(null);
                dameApellidos.setText(null);
                dameEdad.setText(null);
                //Depende de la opcion que esta marcada, la desactiva
                if (radioHombre.isChecked()) {
                    radioHombre.setSelected(true);
                } else {
                    radioMujer.setSelected(false);
                }
                //Vuelve a la posición 0 del spinner
                spinner.setSelection(0);
                //Desactiva el switch
                dameHijos.setChecked(false);

                dameResultado.setText(null);
            }});

        }
    //Creamos este método para cambiar de color del texto del resultado en caso de error
    public void PonerColores(){

        final TextView dameResultado = (TextView) findViewById(R.id.textView9);
        //Pintamos con el color del archivo colors.xml
        dameResultado.setTextColor(dameResultado.getContext().getResources().getColor(R.color.error));


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));//Aplicamos la fuente en un activity
    }


}

