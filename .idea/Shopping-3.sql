-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : mer. 23 avr. 2025 à 10:58
-- Version du serveur : 8.0.35
-- Version de PHP : 8.2.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `Shopping`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_dummy_commands` ()   BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE cmd_id INT;
  DECLARE cnt INT;
  DECLARE minid INT;
  DECLARE art_id INT;

  -- 1) Comptage et ID min
  SELECT COUNT(*), MIN(id_article)
    INTO cnt, minid
    FROM Article;

  IF cnt = 0 THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Aucun article en base, impossible d''insérer des lignes de commande.';
  END IF;

  -- 2) Boucle pour 50 commandes
  WHILE i <= 50 DO
    -- Création de la commande à 10 000 €
    INSERT INTO Commande (date_commande, id_utilisateur, total_commande)
    VALUES (
      DATE_ADD('2025-01-01 10:00:00', INTERVAL i-1 DAY),
      ((i - 1) % 3) + 1,    -- id_utilisateur cyclique 1,2,3
      10000.00
    );
    SET cmd_id = LAST_INSERT_ID();

    -- Choix d'un id_article valide
    SET art_id = minid + ((i - 1) % cnt);

    -- Ligne de commande associée
    INSERT INTO LigneCommande (id_commande, id_article, quantite, prix_total)
    VALUES (
      cmd_id,
      art_id,
      1,
      10000.00
    );

    SET i = i + 1;
  END WHILE;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `Article`
--

CREATE TABLE `Article` (
  `id_article` int NOT NULL,
  `nom` varchar(100) NOT NULL,
  `description` text,
  `prix_unitaire` decimal(10,2) NOT NULL,
  `prix_bulk` decimal(10,2) NOT NULL,
  `quantite_bulk` int NOT NULL,
  `stock` int NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `id_marque` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Article`
--

INSERT INTO `Article` (`id_article`, `nom`, `description`, `prix_unitaire`, `prix_bulk`, `quantite_bulk`, `stock`, `image_path`, `id_marque`) VALUES
(18, 'Pull col rond “Baby Cashmere”', 'Pull 100 % baby cashmere jauge 12, douceur extrême.', 950.00, 8550.00, 10, 24, NULL, 7),
(19, 'Écharpe « Grande Unita »', 'Écharpe tissée en baby cashmere double‑face, franges courtes.', 420.00, 3780.00, 10, 42, NULL, 7),
(20, 'Blazer « Traveller »', 'Veste droite en storm‑system cachemire, déperlante.', 3450.00, 31050.00, 10, 10, NULL, 7),
(21, 'Cardigan cachemire “Marinière”', 'Rayures marine/écru, boutons nacre, jauge 14.', 295.00, 2655.00, 10, 40, NULL, 8),
(22, 'Bonnet torsadé cachemire', 'Bonnet mixte jauge 7, grosses torsades.', 110.00, 990.00, 10, 80, NULL, 8),
(23, 'Poncho oversize', 'Poncho 2‑plis, coupe ample, finition roulottée.', 390.00, 3510.00, 10, 15, NULL, 8),
(24, 'Sweat “Dune” capuche cachemire', 'Coupe hoodie, cordons terminaux métal, 4‑plis.', 320.00, 2880.00, 10, 30, NULL, 9),
(25, 'Jogpant cachemire', 'Pantalon relaxed, bord‑côte cheville.', 260.00, 2340.00, 10, 45, NULL, 9),
(26, 'Pull col V pastel', 'Couleurs sorbet, jauge 12, coupe boyfriend.', 210.00, 1890.00, 10, 50, NULL, 9),
(27, 'Gilet sans manches Ultra‑Light', 'Cachemire ultrafin 149 g, zip double curseur.', 199.00, 1791.00, 10, 55, NULL, 10),
(28, 'Sweat col montant zippé', 'Maille Milano, finitions tubulaires.', 249.00, 2241.00, 10, 35, NULL, 10),
(29, 'Écharpe Light Cashmere', 'Tissage gaze 70 g, bords effrangés.', 89.00, 801.00, 10, 90, NULL, 10),
(30, 'Veste Saharienne cachemire soie', 'Tissage chevron, boutons corne.', 1850.00, 16650.00, 10, 5, NULL, 11),
(31, 'Polo manches longues 2‑plis', 'Maille pleine, boutons nacre australienne.', 650.00, 5850.00, 10, 18, NULL, 11),
(32, 'Plaid “Mangusta” 150 x 200', 'Cachemire double face, point sellier main.', 1250.00, 11250.00, 10, 8, NULL, 11),
(33, 'Manteau “Ludmilla”', 'Long coat ceinturé cachemire pur, coloris camel.', 3050.00, 27450.00, 10, 7, NULL, 12),
(34, 'Pull col cheminée', 'Cachemire/soie, maille jersey, épaule tombante.', 540.00, 4860.00, 10, 22, NULL, 12),
(35, 'Jupe midi côtelée', 'Maille anglaise, fente côté.', 450.00, 4050.00, 10, 18, NULL, 12),
(36, 'Pull argyle classique', 'Motif losange multicolore, côtes 1x1.', 390.00, 3510.00, 10, 28, NULL, 13),
(37, 'Cardigan zippé Sport', 'Col baseball, bicolore, jauge 12.', 420.00, 3780.00, 10, 20, NULL, 13),
(38, 'Écharpe à franges 30 x 180', 'Cachemire écossais peigné.', 160.00, 1440.00, 10, 60, NULL, 13),
(39, 'Écharpe monogramme “Signature”', 'Jacquard beige/noir, 50 x 220.', 240.00, 2160.00, 10, 70, NULL, 14),
(40, 'Pull tube col roulé', 'Coupe droite, manches tombantes.', 490.00, 4410.00, 10, 25, NULL, 14),
(41, 'Hoodie zip cropped', 'Cachemire recyclé, bord‑côte large.', 620.00, 5580.00, 10, 15, NULL, 14),
(42, 'Col bijoux cachemire', 'Pull col bijou strass amovible.', 650.00, 5850.00, 10, 12, NULL, 15),
(43, 'Cape asymétrique', 'Maille fine lurex cachemire, effet liquid‑metal.', 720.00, 6480.00, 10, 9, NULL, 15),
(44, 'Mitaines fines', 'Tricot jauge 14, logo RP brodé.', 120.00, 1080.00, 10, 40, NULL, 15),
(45, 'Pull zippé sporty', 'Col montant, zip argent, poignets contrastants.', 295.00, 2655.00, 10, 30, NULL, 16),
(46, 'Robe pull droite', 'Cachemire/viscose, ceinture amovible.', 350.00, 3150.00, 10, 18, NULL, 16),
(47, 'Bonnet pompon', 'Point mousse, pompon fourrure synthétique.', 90.00, 810.00, 10, 60, NULL, 16),
(48, 'Pull manches ballon', 'Maille côtelée, épaule volumineuse.', 120.00, 1080.00, 10, 40, NULL, 17),
(49, 'Écharpe maxi', 'Cachemire/laine, 70 x 220, bords bruts.', 75.00, 675.00, 10, 90, NULL, 17),
(50, 'Gilet court boutons bijoux', 'Coupe cropped, jauge 12, boutons strass.', 130.00, 1170.00, 10, 35, NULL, 17),
(51, 'Sweat oversize capuche', 'Cachemire doublé, cordons ton sur ton.', 420.00, 3780.00, 10, 22, NULL, 18),
(52, 'Pantalon large “Icare”', 'Maille jersey 12gg, taille coulissée.', 390.00, 3510.00, 10, 18, NULL, 18),
(53, 'Débardeur côtelé', 'Jauge 14, encolure U, coloris ivoire.', 160.00, 1440.00, 10, 45, NULL, 18),
(54, 'Pull torsadé fluo', 'Coloris pistache, col rond.', 195.00, 1755.00, 10, 35, NULL, 19),
(55, 'Jogger cachemire tie‑and‑dye', 'Motif dégradé, coupe unisexe.', 230.00, 2070.00, 10, 28, NULL, 19),
(56, 'Gilet V patch « FF »', 'Couleurs pop, boutons corozo.', 210.00, 1890.00, 10, 20, NULL, 19),
(57, 'Pull maille torsadée', 'Cachemire câble, col rond.', 295.00, 2655.00, 10, 30, NULL, 20),
(58, 'Cardigan perlé', 'Boutons nacre et perles appliquées.', 325.00, 2925.00, 10, 18, NULL, 20),
(59, 'Écharpe côtelée', 'Gris clair, finitions roulottées.', 99.00, 891.00, 10, 70, NULL, 20),
(60, 'Pull col montant élancé', 'Cachemire jauge 16, coupe fittée.', 650.00, 5850.00, 10, 12, NULL, 21),
(61, 'Jupe crayon côtelée', 'Fente arrière, taille haute.', 590.00, 5310.00, 10, 10, NULL, 21),
(62, 'Cape à franges', 'Cachemire/soie, coloris latte.', 880.00, 7920.00, 10, 8, NULL, 21),
(63, 'Sweat Crew classique', 'Design par Gigi Hadid, maille jersey.', 260.00, 2340.00, 10, 25, NULL, 22),
(64, 'Pull rayures colorées', 'Motif irregular stripes, coupe unisexe.', 310.00, 2790.00, 10, 22, NULL, 22),
(65, 'Short lounge', 'Tricot jauge 12, cordon réglable.', 185.00, 1665.00, 10, 40, NULL, 22),
(66, 'Pull col rond “Baby Cashmere”', '100 % baby cashmere, jauge 12, douceur extrême.', 950.00, 8550.00, 10, 25, NULL, 7),
(67, 'Écharpe “Grande Unita”', 'Écharpe double‑face en baby cashmere, franges soignées.', 420.00, 3780.00, 10, 60, NULL, 7),
(68, 'Blazer “Traveller”', 'Storm‑system cachemire déperlant, coupe droite.', 3450.00, 31050.00, 10, 10, NULL, 7),
(69, 'Gilet zippé “Windbreaker”', 'Cachemire léger traité coupe‑vent, finition bords‑côtes.', 2200.00, 19800.00, 10, 15, NULL, 7),
(70, 'Châle “Cashmere Glam”', 'Mélange de baby cashmere et soie, motif jacquard.', 1850.00, 16650.00, 10, 20, NULL, 7),
(71, 'Gucci – Article placeholder 1', 'Description générique pour Gucci', 100.00, 900.00, 10, 10, NULL, 6),
(72, 'Gucci – Article placeholder 2', 'Description générique pour Gucci', 110.00, 990.00, 10, 10, NULL, 6),
(73, 'Gucci – Article placeholder 3', 'Description générique pour Gucci', 120.00, 1080.00, 10, 10, NULL, 6),
(74, 'One‑Sleeve Linen Dress', 'Robe en lin une manche, coupe fluide, longueurs midi.', 180.00, 1620.00, 10, 20, NULL, 17),
(75, 'Rib‑Knit Wool Sweater', 'Pull en laine côtelée, col rond, toucher ultra‑doux.', 220.00, 1980.00, 10, 15, NULL, 17),
(76, 'Pleated Trousers', 'Pantalon plissé taille haute, jambe large, ourlet net.', 160.00, 1440.00, 10, 25, NULL, 17),
(77, 'Leila Lace Blouse', 'Blouse en dentelle ton sur ton, manches longues bouffantes.', 210.00, 1890.00, 10, 18, NULL, 16),
(78, 'Marlon Straight Jeans', 'Jean droit brut, taille mi‑haute, 5 poches classiques.', 145.00, 1305.00, 10, 30, NULL, 16),
(79, 'Romy Midi Dress', 'Robe midi imprimée, décolleté V, bretelles fines ajustables.', 195.00, 1755.00, 10, 22, NULL, 16),
(80, 'Ultra‑Fine Man Sweater', 'Pull homme col V en cachemire ultra‑fin, coupe ajustée.', 295.00, 2655.00, 10, 12, NULL, 8),
(81, 'Cashmere V‑Neck Cardigan', 'Gilet col V en cachemire, boutonnière nacre.', 320.00, 2880.00, 10, 10, NULL, 8),
(82, 'Classic Crewneck Sweater', 'Pull col rond intemporel, 100 % cachemire.', 275.00, 2475.00, 10, 14, NULL, 8),
(83, 'Cashmere Rollneck Sweater', 'Pull col roulé en cashmere léger, manches raglan.', 265.00, 2385.00, 10, 16, NULL, 10),
(84, 'Wool Blend Coat', 'Manteau droit laine‑cachemire, boutonnière simple.', 495.00, 4455.00, 10, 8, NULL, 10),
(85, 'Cashmere Beanie Hat', 'Bonnet en cashmere fin, bordure côtelée.', 75.00, 675.00, 10, 40, NULL, 10),
(86, 'Handwoven Scarf', 'Écharpe tissée main en fibres naturelles, grand format.', 185.00, 1665.00, 10, 20, NULL, 11),
(87, 'Textured Poncho', 'Poncho texturé en laine mélangée, col montant zippé.', 250.00, 2250.00, 10, 12, NULL, 11),
(88, 'Fur‑Trimmed Coat', 'Manteau avec col en fourrure synthétique, coupe trapèze.', 620.00, 5580.00, 10, 6, NULL, 11),
(89, 'Recycled Cashmere Hoodie', 'Hoodie en cachemire recyclé, poche kangourou.', 350.00, 3150.00, 10, 14, NULL, 19),
(90, 'Tech Knit Sweater', 'Pull technique en maille stretch, col montant.', 290.00, 2610.00, 10, 18, NULL, 19),
(91, 'Future Cashmere Blend Jacket', 'Veste zippée en cachemire‑polyester, coupe ergonomique.', 420.00, 3780.00, 10, 10, NULL, 19),
(92, 'GG Marmont Shoulder Bag', 'Sac bandoulière en cuir matelassé avec logo GG.', 1450.00, 13050.00, 10, 5, NULL, 6),
(93, 'Princetown Leather Loafers', 'Mocassins en cuir avec détail fourrure arrière.', 780.00, 7020.00, 10, 7, NULL, 6),
(94, 'GG Supreme Canvas Belt', 'Ceinture toile GG Supreme avec boucle métal.', 420.00, 3780.00, 10, 20, NULL, 6),
(95, 'Logo Embroidered Sweatshirt', 'Sweatshirt en coton bio avec broderie GUR.', 150.00, 1350.00, 10, 25, NULL, 22),
(96, 'Monogram Silk Scarf', 'Foulard en soie imprimé monogramme, dimensions 90×90 cm.', 230.00, 2070.00, 10, 18, NULL, 22),
(97, 'Signature Tote Bag', 'Cabas en toile épaisse avec logo sérigraphié.', 195.00, 1755.00, 10, 22, NULL, 22),
(98, 'Double‑Face Cashmere Coat', 'Manteau réversible 100 % cachemire, grand col châle.', 1850.00, 16650.00, 10, 4, NULL, 9),
(99, 'Relaxed Chinos', 'Pantalon chino ample en coton mélangé, couleur sable.', 155.00, 1395.00, 10, 30, NULL, 9),
(100, 'Crewneck Polo', 'Polo en maille jersey, col classique, manches courtes.', 120.00, 1080.00, 10, 28, NULL, 9),
(101, 'Baby Cashmere Scarf', 'Écharpe en baby cashmere, ultra‑douce et légère.', 650.00, 5850.00, 10, 12, NULL, 7),
(102, 'Vicuna Cape', 'Cape en vicuña, fibres rares, coupe asymétrique.', 5200.00, 46800.00, 10, 2, NULL, 7),
(103, 'Shetland Wool Sweater', 'Pull en laine Shetland, motif torsadé classique.', 980.00, 8820.00, 10, 8, NULL, 7),
(104, 'Knit Crop Cardigan', 'Gilet court en maille côtelée, boutons nacre.', 175.00, 1575.00, 10, 16, NULL, 18),
(105, 'Leather Belt', 'Ceinture en cuir grainé, boucle argentée.', 95.00, 855.00, 10, 40, NULL, 18),
(106, 'Wool Blend Coat', 'Manteau droit laine‑cachemire, doublure soie.', 540.00, 4860.00, 10, 6, NULL, 18),
(107, 'Manuela Camel Coat', 'Iconique manteau en laine camel, ceinture à nouer.', 1290.00, 11610.00, 10, 3, NULL, 12),
(108, 'Silk Blouse', 'Blouse en soie imprimée, coupe fluide, poignets froncés.', 490.00, 4410.00, 10, 12, NULL, 12),
(109, 'Double Face Wool Coat', 'Manteau réversible double‑face, col châle.', 1650.00, 14850.00, 10, 4, NULL, 12),
(110, 'Argyle Knit Sweater', 'Pull motif tartan argyle, col rond.', 340.00, 3060.00, 10, 10, NULL, 13),
(111, 'Cable Knit Cardigan', 'Gilet torsadé boutonné, col châle.', 360.00, 3240.00, 10, 8, NULL, 13),
(112, 'Cashmere Turtleneck', 'Pull col roulé 100 % cashmere, coupe ajustée.', 415.00, 3735.00, 10, 6, NULL, 13),
(113, 'Metallic Fringe Dress', 'Robe mini à franges métalliques holographiques.', 2250.00, 20250.00, 10, 4, NULL, 15),
(114, 'Chainmail Mini Dress', 'Mini‑robe en maille chaîne dorée.', 3150.00, 28350.00, 10, 3, NULL, 15),
(115, 'Sequin Bodysuit', 'Body à sequins, décolleté plongeant.', 950.00, 8550.00, 10, 5, NULL, 15),
(116, 'Oversized Wool Blazer', 'Veste oversize en laine, épaules tombantes.', 450.00, 4050.00, 10, 12, NULL, 20),
(117, 'Silk Printed Blouse', 'Blouse en soie imprimée, manches ballon.', 280.00, 2520.00, 10, 18, NULL, 20),
(118, 'Crochet Knit Cardigan', 'Cardigan crochet maille fine, coupe loose.', 260.00, 2340.00, 10, 15, NULL, 20),
(119, 'Wool Knit Skirt', 'Jupe midi en laine, taille haute.', 350.00, 3150.00, 10, 10, NULL, 14),
(120, 'Cashmere Vest', 'Débardeur en cashmere léger, bords-côtes.', 190.00, 1710.00, 10, 20, NULL, 14),
(121, 'Leather Tote Bag', 'Sac cabas en cuir souple, anses épaisses.', 420.00, 3780.00, 10, 8, NULL, 14),
(122, 'Asymmetric Wool Top', 'Haut asymétrique en laine mérinos, coupe structurée.', 320.00, 2880.00, 10, 14, NULL, 21),
(123, 'High‑Waist Trousers', 'Pantalon taille haute en crêpe, pinces marquées.', 410.00, 3690.00, 10, 10, NULL, 21),
(124, 'Signature Zip Pencil Skirt', 'Jupe crayon zippée, longueur genou.', 380.00, 3420.00, 10, 12, NULL, 21);

-- --------------------------------------------------------

--
-- Structure de la table `Article_Marque`
--

CREATE TABLE `Article_Marque` (
  `id_article` int NOT NULL,
  `id_marque` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `CartItem`
--

CREATE TABLE `CartItem` (
  `user_id` int NOT NULL,
  `article_id` int NOT NULL,
  `quantity` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `CartItem`
--

INSERT INTO `CartItem` (`user_id`, `article_id`, `quantity`) VALUES
(8, 18, 12),
(8, 21, 4);

-- --------------------------------------------------------

--
-- Structure de la table `Commande`
--

CREATE TABLE `Commande` (
  `id_commande` int NOT NULL,
  `id_utilisateur` int NOT NULL,
  `date_commande` datetime NOT NULL,
  `total_commande` decimal(10,2) NOT NULL DEFAULT '0.00',
  `adresse_livraison` varchar(255) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'EN_COURS',
  `date_livraison` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Commande`
--

INSERT INTO `Commande` (`id_commande`, `id_utilisateur`, `date_commande`, `total_commande`, `adresse_livraison`, `status`, `date_livraison`) VALUES
(7, 1, '2025-04-01 10:00:00', 200000.00, NULL, 'EN_COURS', NULL),
(8, 2, '2025-04-05 15:30:00', 150000.00, NULL, 'EN_COURS', NULL),
(9, 3, '2025-02-10 09:45:00', 75000.00, NULL, 'EN_COURS', NULL),
(10, 1, '2025-04-15 18:20:00', 75000.00, NULL, 'EN_COURS', NULL),
(11, 1, '2025-04-01 10:00:00', 200000.00, NULL, 'EN_COURS', NULL),
(12, 2, '2025-04-05 15:30:00', 150000.00, NULL, 'EN_COURS', NULL),
(13, 3, '2025-04-10 09:45:00', 75000.00, NULL, 'EN_COURS', NULL),
(14, 1, '2025-04-15 18:20:00', 75000.00, NULL, 'EN_COURS', NULL),
(15, 1, '2025-01-01 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(16, 1, '2025-01-01 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(17, 1, '2025-01-01 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(18, 2, '2025-01-02 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(19, 3, '2025-01-03 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(20, 1, '2025-01-04 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(21, 2, '2025-01-05 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(22, 3, '2025-01-06 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(23, 1, '2025-01-07 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(24, 2, '2025-01-08 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(25, 3, '2025-01-09 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(26, 1, '2025-01-10 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(27, 2, '2025-01-11 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(28, 3, '2025-01-12 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(29, 1, '2025-01-13 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(30, 2, '2025-01-14 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(31, 3, '2025-01-15 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(32, 1, '2025-01-16 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(33, 2, '2025-01-17 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(34, 3, '2025-01-18 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(35, 1, '2025-01-19 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(36, 2, '2025-01-20 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(37, 3, '2025-01-21 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(38, 1, '2025-01-22 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(39, 2, '2025-01-23 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(40, 3, '2025-01-24 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(41, 1, '2025-01-25 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(42, 2, '2025-01-26 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(43, 3, '2025-01-27 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(44, 1, '2025-01-28 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(45, 2, '2025-01-29 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(46, 3, '2025-01-30 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(47, 1, '2025-01-31 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(48, 2, '2025-02-01 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(49, 3, '2025-02-02 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(50, 1, '2025-02-03 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(51, 2, '2025-02-04 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(52, 3, '2025-02-05 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(53, 1, '2025-02-06 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(54, 2, '2025-02-07 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(55, 3, '2025-02-08 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(56, 1, '2025-02-09 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(57, 2, '2025-02-10 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(58, 3, '2025-02-11 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(59, 1, '2025-02-12 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(60, 2, '2025-02-13 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(61, 3, '2025-02-14 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(62, 1, '2025-02-15 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(63, 2, '2025-02-16 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(64, 3, '2025-02-17 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(65, 1, '2025-02-18 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(66, 2, '2025-02-19 10:00:00', 10000.00, NULL, 'EN_COURS', NULL),
(67, 8, '2025-04-22 16:05:29', 950.00, NULL, 'EN_COURS', NULL),
(68, 8, '2025-04-22 16:08:25', 420.00, NULL, 'ANNULEE', NULL),
(69, 8, '2025-04-22 16:12:26', 420.00, 'rdetfgjhkjlk, xgfchvjbknl 76543', 'EN_COURS', NULL),
(70, 8, '2025-04-22 16:17:23', 420.00, 'tdfhgvjbkjnkm, jklkhjhfgd 76543', 'EN_COURS', NULL),
(71, 8, '2025-04-22 16:38:24', 1134.00, 'kljihugyftrde, lkmjnihbugvyft 45678', 'ANNULEE', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `Discount`
--

CREATE TABLE `Discount` (
  `id_discount` int NOT NULL,
  `description` varchar(255) NOT NULL,
  `taux` decimal(5,2) NOT NULL,
  `id_article` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Discount`
--

INSERT INTO `Discount` (`id_discount`, `description`, `taux`, `id_article`) VALUES
(8, 'black friday', 40.00, 20),
(9, 'solde de mi-saison', 50.00, 24);

-- --------------------------------------------------------

--
-- Structure de la table `LigneCommande`
--

CREATE TABLE `LigneCommande` (
  `id_ligne` int NOT NULL,
  `id_commande` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite` int NOT NULL,
  `prix_total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `LigneCommande`
--

INSERT INTO `LigneCommande` (`id_ligne`, `id_commande`, `id_article`, `quantite`, `prix_total`) VALUES
(14, 17, 18, 1, 10000.00),
(15, 18, 19, 1, 10000.00),
(16, 19, 20, 1, 10000.00),
(17, 20, 21, 1, 10000.00),
(18, 21, 22, 1, 10000.00),
(19, 22, 23, 1, 10000.00),
(20, 23, 24, 1, 10000.00),
(21, 24, 25, 1, 10000.00),
(22, 25, 26, 1, 10000.00),
(23, 26, 27, 1, 10000.00),
(24, 27, 28, 1, 10000.00),
(25, 28, 29, 1, 10000.00),
(26, 29, 30, 1, 10000.00),
(27, 30, 31, 1, 10000.00),
(28, 31, 32, 1, 10000.00),
(29, 32, 33, 1, 10000.00),
(30, 33, 34, 1, 10000.00),
(31, 34, 35, 1, 10000.00),
(32, 35, 36, 1, 10000.00),
(33, 36, 37, 1, 10000.00),
(34, 37, 38, 1, 10000.00),
(35, 38, 39, 1, 10000.00),
(36, 39, 40, 1, 10000.00),
(37, 40, 41, 1, 10000.00),
(38, 41, 42, 1, 10000.00),
(39, 42, 43, 1, 10000.00),
(40, 43, 44, 1, 10000.00),
(41, 44, 45, 1, 10000.00),
(42, 45, 46, 1, 10000.00),
(43, 46, 47, 1, 10000.00),
(44, 47, 48, 1, 10000.00),
(45, 48, 49, 1, 10000.00),
(46, 49, 50, 1, 10000.00),
(47, 50, 51, 1, 10000.00),
(48, 51, 52, 1, 10000.00),
(49, 52, 53, 1, 10000.00),
(50, 53, 54, 1, 10000.00),
(51, 54, 55, 1, 10000.00),
(52, 55, 56, 1, 10000.00),
(53, 56, 57, 1, 10000.00),
(54, 57, 58, 1, 10000.00),
(55, 58, 59, 1, 10000.00),
(56, 59, 60, 1, 10000.00),
(57, 60, 61, 1, 10000.00),
(58, 61, 62, 1, 10000.00),
(59, 62, 63, 1, 10000.00),
(60, 63, 64, 1, 10000.00),
(61, 64, 65, 1, 10000.00),
(62, 65, 66, 1, 10000.00),
(63, 66, 67, 1, 10000.00),
(64, 67, 18, 1, 950.00),
(65, 68, 19, 1, 420.00),
(66, 69, 19, 1, 420.00),
(67, 70, 19, 1, 420.00),
(68, 71, 19, 15, 6300.00);

-- --------------------------------------------------------

--
-- Structure de la table `LignePanier`
--

CREATE TABLE `LignePanier` (
  `id_utilisateur` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Marque`
--

CREATE TABLE `Marque` (
  `id_marque` int NOT NULL,
  `nom` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Marque`
--

INSERT INTO `Marque` (`id_marque`, `nom`) VALUES
(17, '& Other Stories'),
(16, 'ba&sh'),
(8, 'Eric Bompard'),
(10, 'Falconeri'),
(11, 'Franck Namani'),
(19, 'From Future'),
(6, 'Gucci'),
(22, 'Guest In Residence'),
(9, 'Kujten'),
(7, 'Loro Piana'),
(18, 'Loulou Studio'),
(12, 'Max Mara'),
(13, 'Pringle of Scotland'),
(15, 'Rabanne'),
(20, 'Sandro'),
(14, 'Toteme'),
(21, 'Victoria Beckham');

-- --------------------------------------------------------

--
-- Structure de la table `Panier`
--

CREATE TABLE `Panier` (
  `id_utilisateur` int NOT NULL,
  `date_modif` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Utilisateur`
--

CREATE TABLE `Utilisateur` (
  `id_utilisateur` int NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `mot_de_passe` varchar(100) NOT NULL,
  `role` enum('client','admin') NOT NULL DEFAULT 'client'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Utilisateur`
--

INSERT INTO `Utilisateur` (`id_utilisateur`, `nom`, `prenom`, `email`, `mot_de_passe`, `role`) VALUES
(1, 'Dupont', 'Jean', 'jean.dupont@example.com', 'pass123', 'client'),
(2, 'Durand', 'Marie', 'marie.durand@example.com', 'pass123', 'client'),
(3, 'Martin', 'Paul', 'paul.martin@example.com', 'pass123', 'client'),
(4, 'Lefevre', 'Sophie', 'sophie.lefevre@example.com', 'pass123', 'client'),
(5, 'Petit', 'Luc', 'luc.petit@example.com', 'pass123', 'client'),
(6, 'Admin', 'System', 'admin@example.com', 'adminpass', 'admin'),
(8, 'el idrissi', 'malak', 'malakelidrissi@gmail.com', 'adminpass', 'admin');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Article`
--
ALTER TABLE `Article`
  ADD PRIMARY KEY (`id_article`),
  ADD KEY `fk_article_marque` (`id_marque`);

--
-- Index pour la table `Article_Marque`
--
ALTER TABLE `Article_Marque`
  ADD PRIMARY KEY (`id_article`,`id_marque`),
  ADD KEY `id_marque` (`id_marque`);

--
-- Index pour la table `CartItem`
--
ALTER TABLE `CartItem`
  ADD PRIMARY KEY (`user_id`,`article_id`),
  ADD KEY `article_id` (`article_id`);

--
-- Index pour la table `Commande`
--
ALTER TABLE `Commande`
  ADD PRIMARY KEY (`id_commande`),
  ADD KEY `id_utilisateur` (`id_utilisateur`);

--
-- Index pour la table `Discount`
--
ALTER TABLE `Discount`
  ADD PRIMARY KEY (`id_discount`),
  ADD KEY `id_article` (`id_article`);

--
-- Index pour la table `LigneCommande`
--
ALTER TABLE `LigneCommande`
  ADD PRIMARY KEY (`id_ligne`),
  ADD KEY `id_commande` (`id_commande`),
  ADD KEY `id_article` (`id_article`);

--
-- Index pour la table `LignePanier`
--
ALTER TABLE `LignePanier`
  ADD PRIMARY KEY (`id_utilisateur`,`id_article`),
  ADD KEY `id_article` (`id_article`);

--
-- Index pour la table `Marque`
--
ALTER TABLE `Marque`
  ADD PRIMARY KEY (`id_marque`),
  ADD UNIQUE KEY `nom` (`nom`);

--
-- Index pour la table `Panier`
--
ALTER TABLE `Panier`
  ADD PRIMARY KEY (`id_utilisateur`);

--
-- Index pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Article`
--
ALTER TABLE `Article`
  MODIFY `id_article` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=135;

--
-- AUTO_INCREMENT pour la table `Commande`
--
ALTER TABLE `Commande`
  MODIFY `id_commande` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- AUTO_INCREMENT pour la table `Discount`
--
ALTER TABLE `Discount`
  MODIFY `id_discount` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `LigneCommande`
--
ALTER TABLE `LigneCommande`
  MODIFY `id_ligne` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT pour la table `Marque`
--
ALTER TABLE `Marque`
  MODIFY `id_marque` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  MODIFY `id_utilisateur` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Article`
--
ALTER TABLE `Article`
  ADD CONSTRAINT `fk_article_marque` FOREIGN KEY (`id_marque`) REFERENCES `Marque` (`id_marque`);

--
-- Contraintes pour la table `Article_Marque`
--
ALTER TABLE `Article_Marque`
  ADD CONSTRAINT `article_marque_ibfk_1` FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`) ON DELETE CASCADE,
  ADD CONSTRAINT `article_marque_ibfk_2` FOREIGN KEY (`id_marque`) REFERENCES `Marque` (`id_marque`) ON DELETE CASCADE;

--
-- Contraintes pour la table `CartItem`
--
ALTER TABLE `CartItem`
  ADD CONSTRAINT `cartitem_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Utilisateur` (`id_utilisateur`),
  ADD CONSTRAINT `cartitem_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `Article` (`id_article`);

--
-- Contraintes pour la table `Commande`
--
ALTER TABLE `Commande`
  ADD CONSTRAINT `commande_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`) ON DELETE CASCADE;

--
-- Contraintes pour la table `Discount`
--
ALTER TABLE `Discount`
  ADD CONSTRAINT `discount_ibfk_1` FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`) ON DELETE CASCADE;

--
-- Contraintes pour la table `LigneCommande`
--
ALTER TABLE `LigneCommande`
  ADD CONSTRAINT `lignecommande_ibfk_1` FOREIGN KEY (`id_commande`) REFERENCES `Commande` (`id_commande`) ON DELETE CASCADE,
  ADD CONSTRAINT `lignecommande_ibfk_2` FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`) ON DELETE CASCADE;

--
-- Contraintes pour la table `LignePanier`
--
ALTER TABLE `LignePanier`
  ADD CONSTRAINT `lignepanier_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`),
  ADD CONSTRAINT `lignepanier_ibfk_2` FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
