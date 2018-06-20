package br.com.lynx.control.misc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import java.text.ParseException;
import java.util.List;

import br.com.lynx.R;
import br.com.lynx.model.ItemCampanha;
import br.com.lynx.domain.Cliente;

/**
 * Created by rogerio on 15/03/2016.
 */
public class ClienteVerificacaoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_verificacao);

        toolbar = (Toolbar) findViewById(R.id.tb_cliente_verificacao);
        toolbar.setTitle("Verificação de activity_cliente_detalhe");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        cliente = new Cliente(this);
        int clienteID = intent.getIntExtra("ClienteID", 0);
        cliente.load(clienteID);

        viewPager = (ViewPager) findViewById(R.id.vp_tabs);
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigationDrawerLeft.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        slidingTabLayout.setViewPager(viewPager);
    }

    public List<br.com.lynx.model.Titulo> retornaTitulosCliente() throws ParseException {
        return cliente.getTitulos();
    }

    public List<ItemCampanha> retornaItensFaltantesArrastao() {
        return cliente.getItensArrastao();
    }





}
