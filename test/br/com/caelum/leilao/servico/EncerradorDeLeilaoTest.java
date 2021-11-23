package br.com.caelum.leilao.servico;

import java.util.Arrays;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import br.com.caelum.leilao.infra.dao.LeilaoDaoFalso;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

public class EncerradorDeLeilaoTest {
    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAntes() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        /* Cria um teste com Data Builder (criador de dados para teste) */
        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
        
        List<Leilao> leiloesAntigos = Arrays.asList( leilao1, leilao2 );
        /*  LeilaoDao dao = new LeilaoDao(); */
        /* Se rodar mais de uma vez gera problema porque fica armazenado no BD. Para isso se utiliza a classe de Leilao falsa */
        // LeilaoDaoFalso dao = new LeilaoDaoFalso();
        // dao.salva(leilao1);
        // dao.salva(leilao2);
        // Para não ficar criando classes falsas manualmente para testar utiliza-se o mockito.
        LeilaoDao daoFalso = mock( LeilaoDao.class);
        when( daoFalso.correntes() ).thenReturn( leiloesAntigos );

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao( daoFalso );
        encerrador.encerra();

        // List<Leilao> encerrados = dao.encerrados();

        /* Realiza verificações se foram ecerrados os leiloes */
        // assertEquals( 2, encerrados.size( ) );
        // assertTrue( encerrados.get(0).isEncerrado() );
        // assertTrue( encerrados.get(1).isEncerrado() );
        assertEquals(2, encerrador.getTotalEncerrados() );
        assertTrue( leilao1.isEncerrado() );
        assertTrue( leilao2.isEncerrado() );

    }
}
