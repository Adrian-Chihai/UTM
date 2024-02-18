using System;
using System.Security.Cryptography;
using System.Text;

namespace password
{
    class Program
    {
        static void Main(string[] args)
        {
            string hash = "9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08";
            
            Console.WriteLine("Enter your password");

            string password = HideUserInput();

            if (HashPassword(password).ToUpperInvariant().Equals(hash))
            {
                Console.WriteLine("Access Granted!");
            }
            else
            {
                Console.WriteLine("Access Denied!");
            }

        }

        static string HashPassword(string input)
        {
            using (SHA256 sha256 = SHA256.Create())
            {
                byte[] hashBytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(input));
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < hashBytes.Length; i++)
                {
                    builder.Append(hashBytes[i].ToString("x2"));
                }

                return builder.ToString();
            }
        }

        static string HideUserInput()
        {
            string input = "";

            ConsoleKeyInfo key;

            do
            {
                key = Console.ReadKey(intercept: true);

                if (key.Key != ConsoleKey.Enter)
                {
                    input += key.KeyChar;
                    Console.Write("*");
                }
                
            } while (key.Key != ConsoleKey.Enter);
            
            Console.WriteLine();

            return input;
        }
    }
}