import numpy as np
import matplotlib.pyplot as plt

# Setăm parametrii semnalului
frecventa1 = 50  # Hz
frecventa2 = 70  # Hz
durata = 1.0  # secunde
frecventa_esantionare = 1000  # Hz
faza_relativa = np.pi / 4  # ușoară schimbare de fază

# Generăm semnalul
timp = np.linspace(0, durata, int(durata * frecventa_esantionare), endpoint=False)
semnal = np.sin(2 * np.pi * frecventa1 * timp) + np.sin(2 * np.pi * frecventa2 * timp + faza_relativa)

# Efectuăm transformata Fourier
transformata = np.fft.fft(semnal)
frecvente = np.fft.fftfreq(len(transformata), 1 / frecventa_esantionare)

# Plotăm cele trei grafice separate
plt.figure(figsize=(12, 8))

# Semnal în funcție de timp
plt.subplot(3, 1, 1)
plt.plot(timp, semnal, color='tab:red')
plt.title('Semnal în funcție de timp')
plt.xlabel('Timp (s)')
plt.ylabel('Amplitudine')

# Amplitudinea transformatei Fourier în funcție de frecvență
plt.subplot(3, 1, 2)
plt.plot(frecvente, np.abs(transformata), color='tab:blue')
plt.title('Amplitudine Fourier')
plt.xlabel('Frecvență (Hz)')
plt.ylabel('Amplitudine')

# Faza transformatei Fourier în funcție de frecvență
plt.subplot(3, 1, 3)
plt.plot(frecvente, np.angle(transformata), color='tab:green')
plt.title('Fază Fourier')
plt.xlabel('Frecvență (Hz)')
plt.ylabel('Fază')

plt.tight_layout()
plt.show()