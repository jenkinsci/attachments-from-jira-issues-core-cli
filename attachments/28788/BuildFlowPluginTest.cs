using System;
using System.Reflection;
using Microsoft.CSharp;

namespace BuildFlowPluginTest
{
    public class BuildFlowPluginTest
    {

        #region static main function

        [STAThread]
        public static int Main(string[] args)
        {
            for (int i = 0; i < Convert.ToInt32(args[0]); i++)
            {
                Console.WriteLine("");
                for (int j = 0; j < 256; j++)
                {
                   Console.Write(j.ToString()); 
                }
                Console.WriteLine("");

                System.Threading.Thread.Sleep(10);
            }

            return 0;
        }

        #endregion

    }

}
