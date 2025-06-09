# 🔐 CS Gems Final Project – Quadratic Residues, Goldwasser-Micali, and Paillier Encryption Schemes

## 🧭 Pillar of the Project

**Theoretical Depth and Mathematical Foundations of Cryptography**  
This project explores how abstract number-theoretic concepts, particularly quadratic residues, underpin real-world cryptographic systems. The central theme is demonstrating how rigorous mathematical structures such as the Legendre and Jacobi symbols contribute to constructing encryption schemes like **Goldwasser-Micali** and **Paillier**, which achieve **semantic security** and **homomorphic properties**.

## 📄 Project Overview

This project presents a theoretical and comparative analysis of two probabilistic public-key cryptosystems: the **Goldwasser-Micali (GM)** encryption scheme and the **Paillier** encryption scheme.

We begin by introducing the concept of **quadratic residues**, exploring the mathematical tools needed to classify them, including the **Legendre** and **Jacobi** symbols. These ideas lead to the Goldwasser-Micali encryption system, which achieves semantic security through the **Quadratic Residuosity Assumption**.

We then explore the **Paillier cryptosystem**, another probabilistic scheme that is not only semantically secure under the **Decisional Composite Residuosity Assumption (DCRA)**, but also exhibits an **additive homomorphic property**, allowing certain computations on encrypted data without decryption.

The project concludes with a theoretical comparison between GM and Paillier in terms of security assumptions, efficiency, and applications.

## 📚 Contents

- `gems_project.tex`: Main LaTeX file for the report
- `gems_project.pdf`: Compiled PDF
- `README.md`: This file
- Figures or diagrams (if included)

## 🧠 Key Topics Covered

### Mathematical Background
- Modular arithmetic and number theory
- Quadratic residues modulo primes and composites
- Euler’s Criterion
- Legendre and Jacobi symbols

### Cryptographic Schemes

#### 🔸 Goldwasser-Micali (GM)
- Based on the **Quadratic Residuosity Assumption (QRA)**
- Encrypts one bit at a time
- Semantic security through randomness
- Key concepts:
  - Non-residues with Jacobi symbol 1
  - Randomized encryption with indistinguishable ciphertexts

#### 🔸 Paillier Encryption
- Based on the **Decisional Composite Residuosity Assumption (DCRA)**
- Encrypts integers in \[ \mathbb{Z}_n \]
- Supports **additive homomorphism**:
  - \( E(m_1) \cdot E(m_2) = E(m_1 + m_2) \)
- Efficient for applications in secure voting, privacy-preserving computations, and more

- [CS_Gems_Final.pdf](https://github.com/user-attachments/files/20643353/CS_Gems_Final.pdf)

