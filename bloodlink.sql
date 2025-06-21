-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 21, 2025 at 10:57 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bloodlink`
--

-- --------------------------------------------------------

--
-- Table structure for table `donation_schedule`
--

CREATE TABLE `donation_schedule` (
  `id` int(11) NOT NULL,
  `donor_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `hospital_id` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `is_done` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `donation_schedule`
--

INSERT INTO `donation_schedule` (`id`, `donor_id`, `date`, `hospital_id`, `code`, `is_done`) VALUES
(1, 1, '2025-06-23', 2, 'BLD-1750290131915', 1),
(2, 1, '2025-06-25', 2, 'BLD-1750307168931', 0),
(3, 2, '2025-06-23', 3, 'BLD-1750308287866', 1),
(5, 1, '2025-08-19', 3, 'BLD-1750473646508', 0);

-- --------------------------------------------------------

--
-- Table structure for table `donor`
--

CREATE TABLE `donor` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `blood_type` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `donor`
--

INSERT INTO `donor` (`id`, `name`, `email`, `password`, `blood_type`) VALUES
(1, 'Ujang', 'ujang@gmail.com', '12a4002a40cf78f892ff489a9d1a16617613ec75458799e4531430ebd57470fb', 'AB+'),
(2, 'Uki', 'uki@gmail.com', 'b65a9feb9c4224104e2cb39f3275748d59f8723abbefda775ce2e7d3a4984914', 'AB+');

-- --------------------------------------------------------

--
-- Table structure for table `hospital`
--

CREATE TABLE `hospital` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hospital`
--

INSERT INTO `hospital` (`id`, `name`, `email`, `password`, `address`) VALUES
(1, 'RS Sejahtera', 'rssejahtera@gmail.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Ujung Harapan'),
(2, 'RS Udin', 'udin@rsudin.go.id', 'e2a47d699525dfa42419623a2bfcc9a96f4b7a3da2275a720deeeeebefb03a9f', 'Ujung Harapan'),
(3, 'RS Ubaya', 'ubaya@rsubaya.go.id', '244442cb40f7378e3bfcd44d4a2194c84d3f4868f150751551ee96ff4ef38ddd', 'Ujung Harapan');

-- --------------------------------------------------------

--
-- Table structure for table `hospital_blood_supply`
--

CREATE TABLE `hospital_blood_supply` (
  `id` int(11) NOT NULL,
  `hospital_id` int(11) NOT NULL,
  `blood_type` varchar(100) NOT NULL,
  `units` int(11) DEFAULT 0,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hospital_blood_supply`
--

INSERT INTO `hospital_blood_supply` (`id`, `hospital_id`, `blood_type`, `units`, `updated_at`) VALUES
(1, 2, 'AB+', 2, '2025-06-19 04:11:39'),
(2, 3, 'AB+', 1, '2025-06-19 04:45:40');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `donation_schedule`
--
ALTER TABLE `donation_schedule`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`),
  ADD KEY `donor_id` (`donor_id`),
  ADD KEY `hospital_id` (`hospital_id`);

--
-- Indexes for table `donor`
--
ALTER TABLE `donor`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `hospital`
--
ALTER TABLE `hospital`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `hospital_blood_supply`
--
ALTER TABLE `hospital_blood_supply`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hospital_id` (`hospital_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `donation_schedule`
--
ALTER TABLE `donation_schedule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `donor`
--
ALTER TABLE `donor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `hospital`
--
ALTER TABLE `hospital`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `hospital_blood_supply`
--
ALTER TABLE `hospital_blood_supply`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `donation_schedule`
--
ALTER TABLE `donation_schedule`
  ADD CONSTRAINT `donation_schedule_ibfk_1` FOREIGN KEY (`donor_id`) REFERENCES `donor` (`id`);

--
-- Constraints for table `hospital_blood_supply`
--
ALTER TABLE `hospital_blood_supply`
  ADD CONSTRAINT `hospital_blood_supply_ibfk_1` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
