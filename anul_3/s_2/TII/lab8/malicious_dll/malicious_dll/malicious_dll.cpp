#include <windows.h>

BOOL APIENTRY DllMain(HMODULE hModule, DWORD ul_reason_for_call, LPVOID lpReserved)
{
    switch (ul_reason_for_call)
    {
    case DLL_PROCESS_ATTACH:
    {
        const wchar_t* text = L"Folosește acest notepad!";

        wchar_t tempFileName[MAX_PATH];
        GetTempFileNameW(L".", L"notepad_", 0, tempFileName);

        HANDLE hFile = CreateFileW(tempFileName, GENERIC_WRITE, 0, NULL, CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
        if (hFile != INVALID_HANDLE_VALUE)
        {
            DWORD bytesWritten;
            WriteFile(hFile, text, (DWORD)(wcslen(text) * sizeof(wchar_t)), &bytesWritten, NULL);

            CloseHandle(hFile);

            STARTUPINFO si = { sizeof(si) };
            PROCESS_INFORMATION pi;
            if (CreateProcessW(L"notepad.exe", tempFileName, NULL, NULL, FALSE, 0, NULL, NULL, &si, &pi))
            {
                CloseHandle(pi.hProcess);
                CloseHandle(pi.hThread);
            }
            else
            {
                MessageBoxW(NULL, L"Failed to create process!", L"Error", MB_OK | MB_ICONERROR);
            }

            DeleteFileW(tempFileName);
        }
        else
        {
            MessageBoxW(NULL, L"Failed to create file!", L"Error", MB_OK | MB_ICONERROR);
        }

        break;
    }
    case DLL_THREAD_ATTACH:
    case DLL_THREAD_DETACH:
    case DLL_PROCESS_DETACH:
        break;
    }

    return TRUE;
}
