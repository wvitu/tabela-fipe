package br.com.alura.tabelafipe.principal;

import br.com.alura.tabelafipe.model.Dados;
import br.com.alura.tabelafipe.model.Modelos;
import br.com.alura.tabelafipe.model.Veiculo;
import br.com.alura.tabelafipe.service.ConsumoApi;
import br.com.alura.tabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        var menu = """
               *** OPÇÕES ***
               Carro
               Moto
               Caminhão
               
               Digite uma das opções para consulta: 
               """;

        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        if (json == null) {
            System.out.println("❌ Erro ao buscar as marcas. Tente novamente mais tarde.");
            return;
        }
        final var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        String codigoMarca;
        while (true) {
            System.out.println("Informe o código da marca para consulta: ");
            codigoMarca = leitura.nextLine();
            final String codigoMarcaFinal = codigoMarca; // necessário para lambda
            boolean existe = marcas.stream().anyMatch(m -> m.codigo().equals(codigoMarcaFinal));
            if (existe) {
                break;
            }
            System.out.println("Código de marca inválido. Tente novamente.");
        }

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        if (json == null) {
            System.out.println("❌ Erro ao buscar os modelos. Tente novamente mais tarde.");
            return;
        }
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado");
        var nomeVeiculo = leitura.nextLine();

        final var modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        if (modelosFiltrados.isEmpty()) {
            System.out.println("Nenhum modelo encontrado com esse nome.");
            return;
        }

        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        String codigoModelo;
        while (true) {
            System.out.println("Digite por favor o código do modelo para buscar os valores de avaliação: ");
            codigoModelo = leitura.nextLine();
            final String codigoModeloFinal = codigoModelo;
            boolean existe = modelosFiltrados.stream().anyMatch(m -> m.codigo().equals(codigoModeloFinal));
            if (existe) {
                break;
            }
            System.out.println("Código de modelo inválido. Tente novamente.");
        }

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        if (json == null) {
            System.out.println("❌ Erro ao buscar os anos do modelo. Tente novamente mais tarde.");
            return;
        }
        var anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (var ano : anos) {
            var enderecoAnos = endereco + "/" + ano.codigo();
            json = consumo.obterDados(enderecoAnos);
            if (json == null) {
                System.out.println("⚠️ Não foi possível buscar avaliação para o ano " + ano.codigo());
                continue;
            }
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}
