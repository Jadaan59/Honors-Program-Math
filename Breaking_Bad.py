import string
import re
import requests

ALPHABET = string.ascii_lowercase
char_to_int = {ch: i for i, ch in enumerate(ALPHABET)}
int_to_char = {i: ch for i, ch in enumerate(ALPHABET)}

def clean_text(s: str) -> str:
    """Keep only lowercase letters a-z."""
    return "".join(ch for ch in s.lower() if ch in ALPHABET)

def text_to_nums(s: str):
    return [char_to_int[ch] for ch in s]

def nums_to_text(nums):
    return "".join(int_to_char[n] for n in nums)

# --- 1. Ciphertext ---

ciphertext = """
xwyoi zdanq kplim nqlrb sstpv fyhnm glaef erfiz dsvpq gqtrr nnaic zcfzw
wwxaf pbhlj ffsdd wlbvf enebe pecdy xppie abiip dbwvi lyepd wdwle pztcu
xafic llcfy dalxv xftgr bjurk wusvo pjncs vefia lzxbe dtknw xpsok
"""

c_clean = clean_text(ciphertext)
c_nums = text_to_nums(c_clean)
L = len(c_nums)
print("Cipher length:", L)

# compute e_i = c_i - (i-1) mod 26
# here i is 1-based, so subtract index i-1 = j
e_nums = [(c_nums[j] - j) % 26 for j in range(L)]

# --- 2. Load the book ---

# Option A: download directly from Project Gutenberg
url = "https://www.gutenberg.org/cache/epub/12/pg12.txt"
resp = requests.get(url)
book_raw = resp.text

# Option B (if you prefer local file):
# with open("pg12.txt", "r", encoding="utf-8") as f:
#     book_raw = f.read()

book_clean = clean_text(book_raw)
book_nums = text_to_nums(book_clean)
print("Book length:", len(book_nums))

# --- 3. Helper: try to match one book segment ---

def try_match_segment(segment_nums, e_nums):
    """
    Given a candidate plaintext segment (list of m_i) and e_i from ciphertext,
    try to deduce a consistent permutation pi such that e_i = pi(d_i),
    where d_i = (m_i - m_{i-1}) mod 26 with m_0 = 0.

    Returns:
        pi (dict: d -> e) if consistent, else None
    """
    L = len(segment_nums)
    assert L == len(e_nums)

    pi = {}      # mapping d -> e
    prev = 0     # m_0 = 0

    for i in range(L):
        m_i = segment_nums[i]
        d_i = (m_i - prev) % 26
        prev = m_i

        e_i = e_nums[i]

        if d_i in pi:
            if pi[d_i] != e_i:
                return None  # inconsistency
        else:
            pi[d_i] = e_i

    return pi

def invert_mapping(pi):
    """Invert mapping d->e to e->d if possible, otherwise None."""
    inv = {}
    for d, e in pi.items():
        if e in inv and inv[e] != d:
            return None
        inv[e] = d
    return inv

# --- 4. Scan the book for a matching segment ---

match_info = None

for start in range(0, len(book_nums) - L + 1):
    segment = book_nums[start:start+L]
    pi = try_match_segment(segment, e_nums)
    if pi is None:
        continue

    # Invert pi to get pi^{-1} (needed for full decryption check)
    pi_inv = invert_mapping(pi)
    if pi_inv is None:
        continue

    # Try decrypting ciphertext with this partial key and see if we
    # exactly recover the same segment.
    m_dec = []
    prev = 0
    ok = True

    for j in range(L):
        e_j = e_nums[j]
        if e_j not in pi_inv:
            ok = False
            break
        d_j = pi_inv[e_j]
        m_j = (prev + d_j) % 26
        m_dec.append(m_j)
        prev = m_j

    if not ok:
        continue

    # Check if decrypted matches candidate segment
    if m_dec == segment:
        print("Found matching segment at book position:", start)
        match_info = (start, pi, pi_inv, m_dec)
        break

if match_info is None:
    print("No match found (unexpected if everything is correct).")
else:
    start, pi, pi_inv, m_dec = match_info
    plaintext = nums_to_text(m_dec)
    print("Decrypted passage:")
    print(plaintext)

    # Build full 26-letter key string for pi (d -> e), in order of d=0..25
    key_pi = ["?"] * 26
    for d, e in pi.items():
        key_pi[d] = int_to_char[e]
    key_string_pi = "".join(key_pi)
    print("Key (d -> e) as 26-letter string:")
    print(key_string_pi)

    # If you prefer the usual 'a maps to ...' view, this is it:
    print("This means: 0->%s, 1->%s, ..., 25->%s" %
          (key_pi[0], key_pi[1], key_pi[25]))
