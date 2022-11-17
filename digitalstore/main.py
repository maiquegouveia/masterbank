import csv

global usernames_path
global accounts_path
usernames_path = "C:\\Users\\Aluno\\Downloads\\masterbank-main\\digitalstore\\data\\usernames.csv"
accounts_path = "C:\\Users\\Aluno\\eclipse-workspace\\masterbank_final\\src\\data\\accounts.csv"
def login():
    username = input("\nDigite o seu nome de usuário: ").upper()
    password = input("Digite a sua senha: ")
    with open(usernames_path, 'r') as f:
        f_contents = csv.reader(f, delimiter=',')
        for row in f_contents:
            if row == [username, password]:
                print("\nLogin realizado com sucesso.")
                return username
        print("\nNome de usuário ou senha incorretos.")
        return False

def register():
    usernames = []
    with open(usernames_path, "r") as f:
        f_contents = f.readlines()
        for line in f_contents:
            line = line.replace('"','')
            line_content = line.split(',')
            if line_content[0] not in usernames:
                usernames.append(line_content[0])

    username = input("\nDigite o seu nome de usuário: ").upper()
    if username not in usernames:
        password = input("Digite a sua senha: ")
        check = input("Digite a sua senha novamente: ")
        if password == check:
            try:
                with open(usernames_path, "a", newline='') as f:
                    fwriter = csv.writer(f, delimiter=',')
                    fwriter.writerow([username, password])
            except:
                print("\nFalha para criar a conta. Tente novamente mais tarde")
            else:
                print("\nCadastro realizado com sucesso.")
        else:
            print("\nAs senhas não coincidem.")
    else:
        print("\nEste nome de usuário já está cadastrado.")

def payment(total_price):
    check_card_number = input("\nDigite o número do seu cartão: ")
    check_expiration_date =  input("Digite a data de validade no formato 00/00: ")
    check_validation_code = input("Digite o código de verificação: ")

    with open(accounts_path, 'r') as f:
        lines = f.readlines()
    with open(accounts_path, 'w', newline='') as f:
        exception = 0
        found = False
        fwriter = csv.writer(f, delimiter=',', quoting=csv.QUOTE_ALL)
        for line in lines:
            line = line.replace('"', '')
            line_content = line.split(',')
            cpf = line_content[0]
            balance = line_content[1]
            investments = line_content[2]
            card_number = line_content[3]
            expiration_date = line_content[4]
            validation_code = line_content[5]
            pix = line_content[6]
            pix = pix.replace('\n', '')
            if check_card_number == card_number and check_expiration_date == expiration_date and check_validation_code == validation_code:
                found = True
                double_balance = float(balance)
                if double_balance >= total_price:
                    double_balance = double_balance - total_price
                    try:
                        data = [cpf, str(double_balance), investments, card_number, expiration_date, validation_code, pix]
                        fwriter.writerow(data)
                    except Exception:
                        exception = 1
                    else:
                        print("\nCompra finalizada com sucesso.")
                else:
                    print("\nNão foi possível completar a compra.")
                    try:
                        data = [cpf, balance, investments, card_number, expiration_date, validation_code, pix]
                        fwriter.writerow(data)
                    except Exception:
                        exception = 1
            else:
                try:
                    data = [cpf, balance, investments, card_number, expiration_date, validation_code, pix]
                    fwriter.writerow(data)
                except Exception:
                    exception = 1

        if found == False:
            print("\nCartão inválido.")
        else:
            if exception == 1:
                print("\nOcorreu um erro durante a compra. Tente novamente mais tarde.")

while True:
    print("\nBem vindo(a) a loja SuplexMaster!")
    print("\n1 - Entrar na minha conta")
    print("2 - Fazer cadastro")
    print("3 - Sair\n")
    print("Escolha uma opção -->")
    option = input()

    if option == "1":
        answer = login()
        if answer != False:
            while True:
                print("\nOlá,",answer + "!")
                print("\n1 - Comprar Agora")
                print("2 - Sair\n")
                print("Escolha uma opção -->")
                menu = input()
                if menu == "1":
                    while True:
                        print("\nNossos Produtos")
                        print("\n1 - Creatina Creapure 250g - Preço: R$ 100")
                        print("2 - Whey Protein de Chocolate 1kg - Preço: R$ 100")
                        print("3 - Voltar ao menu\n")
                        print("Escolha uma opção -->")
                        product_option = input()
                        if product_option == "1" or product_option == "2":
                            amount = int(input("\nDigite a quantidade do produto: "))
                            while True:
                                total = 100*amount
                                print("\nValor do pedido: R$",total)
                                answer_product = input("\nDeseja prosseguir para o pagamento? Sim ou Não? ").lower()
                                if answer_product == 'sim':
                                    payment(total)
                                    break
                                elif answer_product == 'não':
                                    print("\nCompra cancelada.")
                                    break
                                else:
                                    print("\nOpção inválida.")

                        elif product_option == "3":
                            break
                        else:
                            print("\nOpção inválida.")
                elif menu == "2":
                    print("\nSaindo da conta.")
                    break
                else:
                    print("\nOpção inválida.")
    elif option == "2":
        register()
    elif option == "3":
        print("\nPrograma encerrado.")
        break
    else:
        print("\nOpção inválida.")
