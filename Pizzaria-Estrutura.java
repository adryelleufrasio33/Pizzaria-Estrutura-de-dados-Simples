import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

class Pizzaria {
    private List<Pizza> menu;
    private Queue<Pedido> pedidos;
    private Stack<Pizza> pedidosEmAndamento;
    private BinaryTree<Pizza> pizzaTree; // Árvore binária para o menu

    public Pizzaria() {
        menu = new ArrayList<>();
        pedidos = new LinkedList<>();
        pedidosEmAndamento = new Stack<>();
        pizzaTree = new BinaryTree<>();
    }

    public void adicionarPizza(Pizza pizza) {
        menu.add(pizza);
        pizzaTree.insert(pizza); // Adiciona a pizza à árvore
    }

    public void fazerPedido(String nomePizza) {
        Pizza pizza = encontrarPizza(nomePizza);
        if (pizza != null) {
            Pedido pedido = new Pedido(pizza);
            pedidos.offer(pedido);
        } else {
            System.out.println("Pizza não encontrada no menu: " + nomePizza);
        }
    }

public void fazerPedidoPorNumero(int numeroPizza) {
    if (numeroPizza > 0 && numeroPizza <= menu.size()) {
        Pizza pizzaEscolhida = menu.get(numeroPizza - 1);
        Pedido pedido = new Pedido(pizzaEscolhida);

        boolean pedidoJaAdicionado = pedidos.contains(pedido); // Check if the order already exists

        if (!pedidoJaAdicionado) {
            pedidos.offer(pedido);
            System.out.println("Pedido feito: " + pizzaEscolhida.getNome());
        } else {
            System.out.println("Pedido já feito: " + pizzaEscolhida.getNome());
        }
    } else {
        System.out.println("Número de pizza inválido. Por favor, escolha um número válido do menu.");
    }
}


    public void listarMenu() {
        System.out.println("Menu:");
        int numeroPizza = 1;
        for (Pizza pizza : menu) {
            System.out.println(numeroPizza + ". " + pizza.getNome() + " - R$" + pizza.getPreco());
            numeroPizza++;
        }
    }

    public void atenderPedidos() {
        while (!pedidos.isEmpty()) {
            Pedido pedido = pedidos.poll();
            System.out.println("Preparando a pizza: " + pedido.getPizza().getNome());
            pedido.getPizza().preparar();
            pedidosEmAndamento.push(pedido.getPizza());
            System.out.println("Entregando a pizza: " + pedido.getPizza().getNome());
            pedido.getPizza().entregar();
            pedidosEmAndamento.pop();
        }
    }

    public void listarPedidosEmAndamento() {
        System.out.println("Pedidos em andamento:");
        for (Pizza pizza : pedidosEmAndamento) {
            System.out.println("- " + pizza.getNome());
        }
    }

    public void listarMenuArvore() {
        System.out.println("Menu (árvore binária):");
        pizzaTree.inOrderTraversal();
    }

    private Pizza encontrarPizza(String nomePizza) {
        for (Pizza pizza : menu) {
            if (pizza.getNome().equalsIgnoreCase(nomePizza)) {
                return pizza;
            }
        }
        return null;
    }
}

class Pedido {
    private Pizza pizza;

    public Pedido(Pizza pizza) {
        this.pizza = pizza;
    }

    public Pizza getPizza() {
        return pizza;
    }
}

class Pizza implements Comparable<Pizza> {
    private String nome;
    private double preco;

    public Pizza(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void preparar() {
        System.out.println("Preparando a pizza: " + nome);
    }

    public void entregar() {
        System.out.println("Entregando a pizza: " + nome);
    }

    @Override
    public int compareTo(Pizza otherPizza) {
        // Comparar as pizzas com base no nome
        return this.nome.compareTo(otherPizza.nome);
    }
}

class BinaryTree<T extends Comparable<T>> {
    private TreeNode<T> root;

    public void insert(T data) {
        root = insert(root, data);
    }

    private TreeNode<T> insert(TreeNode<T> node, T data) {
        if (node == null) {
            return new TreeNode<>(data);
        }

        int compareResult = data.compareTo(node.data);

        if (compareResult < 0) {
            node.left = insert(node.left, data);
        } else if (compareResult > 0) {
            node.right = insert(node.right, data);
        } else {
            // Pizza já existe na árvore
            return node;
        }

        // Balancear a árvore
        node = balance(node);

        return node;
    }

    private TreeNode<T> balance(TreeNode<T> node) {
        // Rotina de balanceamento da árvore AVL
        return node;
    }

    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(TreeNode<T> node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println("- " + node.data.toString());
            inOrderTraversal(node.right);
        }
    }
}

class TreeNode<T> {
    T data;
    TreeNode<T> left;
    TreeNode<T> right;

    public TreeNode(T data) {
        this.data = data;
        left = null;
        right = null;
    }
}


public class Mavenproject3{
    public static void main(String[] args) {
        Pizzaria pizzaria = new Pizzaria();
        pizzaria.adicionarPizza(new Pizza("Calabresa", 25.0));
        pizzaria.adicionarPizza(new Pizza("Margherita", 22.0));
        pizzaria.adicionarPizza(new Pizza("Pepperoni", 28.0));

        pizzaria.listarMenu();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha uma pizza pelo número: ");
        int numeroEscolhido = scanner.nextInt();
        pizzaria.fazerPedidoPorNumero(numeroEscolhido);

        pizzaria.atenderPedidos();

        pizzaria.listarPedidosEmAndamento();

        pizzaria.listarMenuArvore();
        
        System.out.println("Muito obrigado por escolher a pizzarria Faz L DU");
    }
}
