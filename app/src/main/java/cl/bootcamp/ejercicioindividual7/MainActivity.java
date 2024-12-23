package cl.bootcamp.ejercicioindividual7;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cl.bootcamp.ejercicioindividual7.fragments.BulbasaurFragment;
import cl.bootcamp.ejercicioindividual7.fragments.CharmanderFragment;
import cl.bootcamp.ejercicioindividual7.fragments.DefaultFragment;
import cl.bootcamp.ejercicioindividual7.fragments.SquirtleFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup pokemonGroup;
    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonGroup = findViewById(R.id.pokemon_group);
        validateButton = findViewById(R.id.validate_button);

        loadFragment(new DefaultFragment());

        validateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick() {
                onClick(null);
            }

            @Override
            public void onClick(View view) {
                int selectId = pokemonGroup.getCheckedRadioButtonId();
                if (selectId == -1) {
                    Toast.makeText(MainActivity.this, "Selecciona un Pokemon", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedPokemon = findViewById(selectId);
                    String pokemonName = selectedPokemon.getText().toString();
                    showPokemonDialog(pokemonName);
                    updateBackgroundFragment(pokemonName);
                }
            }
        });
    }

    private void showPokemonDialog(String pokemonName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_pokemon, null);

        AlertDialog dialog = builder.create();
        dialog.findViewById(R.id.dialog_close_button).setOnClickListener(v -> dialog.dismiss());

        TextView title = dialogView.findViewById(R.id.dialog_title);
        TextView pokemonSelected = dialog.findViewById(R.id.dialog_pokemon_name);

        title.setText("Pokémon Seleccionado");
        pokemonSelected.setText(pokemonName);

        if (pokemonName.equalsIgnoreCase("Charmander")) {
            pokemonSelected.setTextColor(getColorCompat(R.color.charmander));

        } else if (pokemonName.equalsIgnoreCase("Bulbasaur")) {
            pokemonSelected.setTextColor(getColorCompat(R.color.bulbasaur));

        } else if (pokemonName.equalsIgnoreCase("Squirtle")) {
            pokemonSelected.setTextColor(getColorCompat(R.color.squirtle));

        }
        dialog.show();
    }

    private void updateBackgroundFragment(String pokemonName) {
        Fragment fragment;

        switch (pokemonName.toLowerCase()) {
            case "charmander":
                fragment = new CharmanderFragment();
                break;
            case "bulbasaur":
                fragment = new BulbasaurFragment();
                break;
            case "squirtle":
                fragment = new SquirtleFragment();
                break;
            default:
                fragment = new DefaultFragment();
        }
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private int getColorCompat(int colorResId) {
        return getResources().getColor(colorResId, getTheme());
    }
}