package fr.Alcoolemie;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private float coefDiffu = (float) 0.7; // coefficient de diffusion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ecouteRadio();
        ecouteCalcul();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void ecouteRadio() {
    	((RadioGroup)findViewById(R.id.grpRadioSexe)).setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
   	public void onCheckedChanged(RadioGroup group, int checkedId) {
   	if (((RadioButton)findViewById(R.id.rdHomme)).isChecked()) {
    	Toast.makeText(MainActivity.this, "Homme", Toast.LENGTH_SHORT).show();
    	coefDiffu = (float) 0.7 ;
   	} else {
    	Toast.makeText(MainActivity.this, "Femme", Toast.LENGTH_SHORT).show();
    	coefDiffu = (float) 0.6;
   	}
   	}
   	});
   }
    
    private void ecouteCalcul() {
    	((Button)findViewById(R.id.btnCalc)).setOnClickListener(new Button.OnClickListener() {
    	public void onClick(View v) {
    		String txtPoids =  
    		((EditText)findViewById(R.id.txtPoids)).getText().toString();
    		String txtNbVerre = ((EditText)findViewById(R.id.txtnbVerre)).getText().toString();
    	if ((!(txtPoids.equals(""))) && (!(txtNbVerre.equals("")))) {
    	// calcul
    		calcTxAlcool(Integer.parseInt(txtPoids), Integer.parseInt(txtNbVerre)) ;
    		
    	} else{
    		Toast.makeText(MainActivity.this, "Veuillez saisir tous les champs !", Toast.LENGTH_SHORT).show();
    	}
    	}
    	});
    }
    
    private void calcTxAlcool(int poids, int nbVerres) {
    	int uniteAlcool = 10 ; // unité d'alcool
    	// contrôle que les valeurs ne sont pas nulles
    	if (poids != 0 && nbVerres != 0) {
    		float tauxAlcool = (nbVerres * uniteAlcool) / (poids * coefDiffu) ;
    		gestionAffichage(tauxAlcool) ;
    	}
    }
    
    public void gestionAffichage(float taux) {
    	// récupération des 2 objets graphiques à modifier
    	TextView lblTxAlcool = (TextView) findViewById(R.id.lblTxAlcool);
    	ImageView imgSmiley = (ImageView) findViewById(R.id.imgSmiley);
    	// tests sur le taux
    	if (taux < 0.5) {
    		lblTxAlcool.setTextColor(Color.GREEN);
    		lblTxAlcool.setText("Bonne route !");
    		imgSmiley.setImageResource(R.drawable.ok);
    	}else{
	    	lblTxAlcool.setTextColor(Color.RED);
	    	int coef ;
	    		if (taux < 0.7) {
	    		coef = 2 ;
	    		imgSmiley.setImageResource(R.drawable.deux);
	    	}else{
	    		if (taux < 0.8) {
	    			coef = 5 ;
	    			imgSmiley.setImageResource(R.drawable.cinq);
	    		}else{
			    	if (taux < 1.2) {
			    		coef = 10 ;
			    		imgSmiley.setImageResource(R.drawable.dix);
			    	}else{
				    	if (taux < 2) {
				    		coef = 35 ;
				    		imgSmiley.setImageResource(R.drawable.trentecinq);
				    	}else{
				    		coef = 80 ;
				    		imgSmiley.setImageResource(R.drawable.quatrevingt);
				    	}
			    	}
	    		}
	    	}
    	lblTxAlcool.setText(String.format("%.01f", taux)+"g d'alcool = risque accident mortel x "+coef+" !");
    	}
    }
}
