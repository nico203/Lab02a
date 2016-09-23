package ns.dam.isi.frsf.utn.edu.ar.lab02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    private ToggleButton tipoReservacionButton;
    private Spinner horarioReserva;
    private Switch notificarAntes;
    private RadioGroup radioGroup;
    private Button agregarButton;
    private Button confirmarButton;
    private Button reiniciarButton;
    private ListView listView;
    private TextView infoTextView;

    private ElementoMenu[] listaBebidas;
    private ElementoMenu[] listaPlatos;
    private ElementoMenu[] listaPostre;
    private ElementoMenu elementoSeleccionado;
    private ArrayList<ElementoMenu> seleccionMenu;
    private boolean estadoPedidoConfirmado;

    private void iniciarListas() {
        // inicia lista de bebidas
        listaBebidas = new ElementoMenu[7];
        listaBebidas[0] = new ElementoMenu(1, "Coca");
        listaBebidas[1] = new ElementoMenu(4, "Jugo");
        listaBebidas[2] = new ElementoMenu(6, "Agua");
        listaBebidas[3] = new ElementoMenu(8, "Soda");
        listaBebidas[4] = new ElementoMenu(9, "Fernet");
        listaBebidas[5] = new ElementoMenu(10, "Vino");
        listaBebidas[6] = new ElementoMenu(11, "Cerveza");

        // inicia lista de platos
        listaPlatos = new ElementoMenu[14];
        listaPlatos[0] = new ElementoMenu(1, "Ravioles");
        listaPlatos[1] = new ElementoMenu(2, "Gnocchi");
        listaPlatos[2] = new ElementoMenu(3, "Tallarines");
        listaPlatos[3] = new ElementoMenu(4, "Lomo");
        listaPlatos[4] = new ElementoMenu(5, "Entrecot");
        listaPlatos[5] = new ElementoMenu(6, "Pollo");
        listaPlatos[6] = new ElementoMenu(7, "Pechuga");
        listaPlatos[7] = new ElementoMenu(8, "Pizza");
        listaPlatos[8] = new ElementoMenu(9, "Empanadas");
        listaPlatos[9] = new ElementoMenu(10, "Milanesas");
        listaPlatos[10] = new ElementoMenu(11, "Picada 1");
        listaPlatos[11] = new ElementoMenu(12, "Picada 2");
        listaPlatos[12] = new ElementoMenu(13, "Hamburguesa");
        listaPlatos[12] = new ElementoMenu(14, "Calamares");

        // inicia lista de postres
        listaPostre = new ElementoMenu[15];
        listaPostre[0] = new ElementoMenu(1, "Helado");
        listaPostre[1] = new ElementoMenu(2, "Ensalada de Frutas");
        listaPostre[2] = new ElementoMenu(3, "Macedonia");
        listaPostre[3] = new ElementoMenu(4, "Brownie");
        listaPostre[4] = new ElementoMenu(5, "Cheescake");
        listaPostre[5] = new ElementoMenu(6, "Tiramisu");
        listaPostre[6] = new ElementoMenu(7, "Mousse");
        listaPostre[7] = new ElementoMenu(8, "Fondue");
        listaPostre[8] = new ElementoMenu(9, "Profiterol");
        listaPostre[9] = new ElementoMenu(10, "Selva Negra");
        listaPostre[10] = new ElementoMenu(11, "Lemon Pie");
        listaPostre[11] = new ElementoMenu(12, "KitKat");
        listaPostre[12] = new ElementoMenu(13, "IceCreamSandwich");
        listaPostre[13] = new ElementoMenu(14, "Frozen Yougurth");
        listaPostre[14] = new ElementoMenu(15, "Queso y Batata");
    }

    private void clickConfirmarButton() {
        estadoPedidoConfirmado = true;
        DecimalFormat f = new DecimalFormat("##.00");
        Double total = new Double(0.0);
        for(ElementoMenu e : seleccionMenu) {
            total += e.getPrecio();
        }
        infoTextView.append("Total: " + f.format(total));
    }

    private void clickAgregarButton() {
        if(estadoPedidoConfirmado){
            Toast toast = Toast.makeText(getApplicationContext(), "No es posible agregar el elemento porque el pedido ya fue confirmado.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(elementoSeleccionado == null){
            Toast toast = Toast.makeText(getApplicationContext(), "Debe seleccionar algo del menú.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        seleccionMenu.add(elementoSeleccionado);
        infoTextView.append(elementoSeleccionado.toString() + "\n");
        listView.clearChoices();
        listView.setItemChecked(-1, true);
    }

    private void clickReiniciarButton() {
        tipoReservacionButton.setChecked(false);
        horarioReserva.setSelection(0);
        notificarAntes.setChecked(false);
        radioGroup.clearCheck();
        elementoSeleccionado = null;
        estadoPedidoConfirmado = false;
        listView.clearChoices();
        listView.setItemChecked(-1, true);
        infoTextView.setText("");
        seleccionMenu.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tipoReservacionButton = (ToggleButton) findViewById(R.id.tipoReservacionButton);
        horarioReserva = (Spinner) findViewById(R.id.horarioReserva);
        notificarAntes = (Switch) findViewById(R.id.notificarAntes);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
        agregarButton = (Button) findViewById(R.id.agregarButton);
        agregarButton.setOnClickListener(this);
        confirmarButton = (Button) findViewById(R.id.confirmarButton);
        confirmarButton.setOnClickListener(this);
        reiniciarButton = (Button) findViewById(R.id.reiniciarButton);
        reiniciarButton.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);
        infoTextView = (TextView) findViewById(R.id.infoTextView);
        infoTextView.setMovementMethod(new ScrollingMovementMethod());
        iniciarListas();
        elementoSeleccionado = null;
        estadoPedidoConfirmado = false;
        seleccionMenu = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.agregarButton){
            this.clickAgregarButton();
        } else if (v.getId() == R.id.confirmarButton){
            this.clickConfirmarButton();
        } else if (v.getId() == R.id.reiniciarButton){
            this.clickReiniciarButton();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        elementoSeleccionado = null;
        ArrayList<ElementoMenu> lista = new ArrayList<>();
        switch (checkedId) {
            case -1:
                break;
            case R.id.platoRB:
                lista.addAll(Arrays.asList(listaPlatos));
                break;
            case R.id.postreRB:
                lista.addAll(Arrays.asList(listaPostre));
                break;
            case R.id.bebidaRB:
                lista.addAll(Arrays.asList(listaBebidas));
                break;
        }
        ArrayAdapter<ElementoMenu> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, lista);;
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        elementoSeleccionado = (ElementoMenu) listView.getItemAtPosition(position);
    }
}
