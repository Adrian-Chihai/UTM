using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Runtime.InteropServices;
using System.Text;
using System.Text.Json;

namespace ProcessInfoExtractor
{
    class Program
    {
        // Structura pentru stocarea informațiilor despre procese
        public class ProcessInfo
        {
            public int ProcessId { get; set; }
            public string ExecutablePath { get; set; }
            public string DigitalSignatureStatus { get; set; }
        }

        // Importarea funcțiilor WinAPI necesare
        [DllImport("psapi.dll", SetLastError = true)]
        static extern bool EnumProcesses([Out] uint[] processIds, int arraySizeBytes, [Out] out int bytesCopied);

        [DllImport("kernel32.dll", SetLastError = true)]
        static extern IntPtr OpenProcess(uint processAccess, bool bInheritHandle, int processId);

        [DllImport("psapi.dll", SetLastError = true, CharSet = CharSet.Auto)]
        static extern bool GetModuleFileNameEx(IntPtr hProcess, IntPtr hModule, [Out] StringBuilder lpBaseName, [In][MarshalAs(UnmanagedType.U4)] int nSize);

        [DllImport("kernel32.dll", SetLastError = true)]
        static extern bool CloseHandle(IntPtr hObject);

        [DllImport("wintrust.dll", SetLastError = true)]
        static extern uint WinVerifyTrust(IntPtr hwnd, [MarshalAs(UnmanagedType.LPStruct)] Guid pgActionID, IntPtr pWVTData);

        [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
        public class WinTrustFileInfo
        {
            public UInt32 StructSize = (UInt32)Marshal.SizeOf(typeof(WinTrustFileInfo));
            public IntPtr pszFilePath;
            public IntPtr hFile = IntPtr.Zero;
            public IntPtr pgKnownSubject = IntPtr.Zero;
        }

        [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
        public class WinTrustData
        {
            public UInt32 StructSize = (UInt32)Marshal.SizeOf(typeof(WinTrustData));
            public IntPtr PolicyCallbackData = IntPtr.Zero;
            public IntPtr SIPClientData = IntPtr.Zero;
            public UInt32 UIChoice = 2; // WTD_UI_NONE
            public UInt32 RevocationChecks = 0;
            public UInt32 UnionChoice = 1; // WTD_CHOICE_FILE
            public IntPtr FileInfoPtr;
            public UInt32 StateAction = 0;
            public IntPtr StateData = IntPtr.Zero;
            public String URLReference = null;
            public UInt32 ProvFlags = 0x00000010; // WTD_REVOCATION_CHECK_NONE
            public UInt32 UIContext = 0;
        }

        static readonly IntPtr INVALID_HANDLE_VALUE = new IntPtr(-1);
        static readonly Guid WINTRUST_ACTION_GENERIC_VERIFY_V2 = new Guid(0xaac56b, 0xcd44, 0x11d0, 0x8c, 0xc2, 0x00, 0xc0, 0x4f, 0xc2, 0x94, 0x66);

        static void Main(string[] args)
        {
            List<ProcessInfo> processList = new List<ProcessInfo>();

            uint[] processIds = new uint[1024];
            int bytesCopied;

            // Obținerea listei de procese
            if (EnumProcesses(processIds, processIds.Length * sizeof(uint), out bytesCopied))
            {
                int numProcesses = bytesCopied / sizeof(uint);

                for (int i = 0; i < numProcesses; i++)
                {
                    IntPtr hProcess = OpenProcess(0x0410, false, (int)processIds[i]); // PROCESS_QUERY_INFORMATION | PROCESS_VM_READ

                    if (hProcess != IntPtr.Zero)
                    {
                        StringBuilder processName = new StringBuilder(1024);
                        if (GetModuleFileNameEx(hProcess, IntPtr.Zero, processName, processName.Capacity))
                        {
                            string signatureStatus = CheckSignature(processName.ToString());

                            processList.Add(new ProcessInfo
                            {
                                ProcessId = (int)processIds[i],
                                ExecutablePath = processName.ToString(),
                                DigitalSignatureStatus = signatureStatus
                            });
                        }

                        CloseHandle(hProcess);
                    }
                }
            }

            // Serializarea listei de procese în format JSON
            string json = JsonSerializer.Serialize(processList, new JsonSerializerOptions { WriteIndented = true });

            // Salvarea JSON-ului într-un fișier
            File.WriteAllText("E:\\UTM\\anul_3\\s_2\\PMRI\\lab4\\processes.json", json);

            Console.WriteLine("Informațiile despre procese au fost salvate în 'processes.json'.");
        }

        static string CheckSignature(string filePath)
        {
            WinTrustFileInfo wtfi = new WinTrustFileInfo
            {
                pszFilePath = Marshal.StringToCoTaskMemAuto(filePath)
            };

            WinTrustData wtd = new WinTrustData
            {
                UnionChoice = 1, // WTD_CHOICE_FILE
                FileInfoPtr = Marshal.AllocCoTaskMem(Marshal.SizeOf(typeof(WinTrustFileInfo)))
            };

            Marshal.StructureToPtr(wtfi, wtd.FileInfoPtr, false);

            IntPtr pWVTData = Marshal.AllocCoTaskMem(Marshal.SizeOf(typeof(WinTrustData)));
            Marshal.StructureToPtr(wtd, pWVTData, false);

            uint result = WinVerifyTrust(INVALID_HANDLE_VALUE, WINTRUST_ACTION_GENERIC_VERIFY_V2, pWVTData);

            // Curățare memorie alocată
            Marshal.FreeCoTaskMem(wtd.FileInfoPtr);
            Marshal.FreeCoTaskMem(wtfi.pszFilePath);
            Marshal.FreeCoTaskMem(pWVTData);

            switch (result)
            {
                case 0:
                    return "Trusted";
                case 0x800B0100:
                    return "No signature";
                case 0x80096010:
                    return "Bad signature";
                default:
                    return $"Unknown error: 0x{result:X}";
            }
        }
    }
}
