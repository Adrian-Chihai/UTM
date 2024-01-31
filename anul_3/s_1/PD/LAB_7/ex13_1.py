import numpy as np
import matplotlib.pyplot as plt
from skimage import io, color, util, restoration

# Descarcă o imagine de exemplu (înlocuiește cu propria imagine dacă este necesar)
url_imagine = "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.pixelstalk.net%2Fwp-content%2Fuploads%2F2016%2F07%2FLiverpool-HD-Picture.jpg&f=1&nofb=1&ipt=8b74c59ece6d23db798e3beb0bc6a79c33f90b6df005db0588fbf46e7c8b07e7&ipo=images"
imagine = io.imread(url_imagine)

# Converteste imaginea la tonuri de gri
imagine_gri = color.rgb2gray(imagine)

# Generează zgomot pe imagine
imagine_cu_zgomot = util.random_noise(imagine_gri, mode='s&p', rng=None)

# Creează un PSF Gaussian simplu (2D)
psf = np.ones((5, 5)) / 25  # Presupunând un filtru de medie simplu

# Aplică filtrul Wiener
imagine_wiener = restoration.wiener(imagine_cu_zgomot, psf, balance=0.1)

# Afișează imaginile
plt.figure(figsize=(12, 6))

plt.subplot(1, 3, 1)
plt.imshow(imagine_cu_zgomot, cmap='gray')
plt.title('Imagine cu Zgomot')

plt.subplot(1, 3, 2)
plt.imshow(imagine_wiener, cmap='gray')
plt.title('Filtru Wiener')

plt.tight_layout()
plt.show()
