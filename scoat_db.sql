-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 13, 2022 at 10:11 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `scoat_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendances`
--

CREATE TABLE `attendances` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `student_id` bigint(20) UNSIGNED NOT NULL,
  `course_id` bigint(20) UNSIGNED NOT NULL,
  `location_id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `attendances`
--

INSERT INTO `attendances` (`id`, `student_id`, `course_id`, `location_id`, `created_at`, `updated_at`) VALUES
(1, 2, 1, 1, '2022-02-03 14:34:08', '2022-02-03 14:34:08'),
(2, 3, 3, 2, '2022-02-03 15:18:44', '2022-02-03 15:18:44');

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `lecturer_id` bigint(20) UNSIGNED NOT NULL,
  `course` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `course_url_link` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time_start` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time_end` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`id`, `lecturer_id`, `course`, `course_url_link`, `time_start`, `time_end`, `created_at`, `updated_at`) VALUES
(1, 1, 'Lectures 12 - Architectural patterns of websites for mobile devices', 'https://teams.microsoft.com/l/meetup-join/19%3ameeting_OWQ2MjlkODctN2YyZC00ZjEwLTk0NzYtMjI3YTY5ODQ2ZGVm%40thread.v2/0?context=%7b%22Tid%22%3a%22e8a52afe-6ea8-47f7-b275-783f7087b5fa%22%2c%22Oid%22%3a%223fce7771-2c7e-49c6-999b-58c37f86a484%22%7d', '2022-01-20T12:30:50', '2022-01-20T14:30:50', '2022-02-03 14:32:11', '2022-02-03 15:24:11'),
(3, 1, 'Lecture 4', 'https://teams.microsoft.com/l/meetup-join/19%3ameeting_YTIzMzliNzMtZTIxZS00OTFhLWE1NDEtZTk2YmZjMWI0NzNl%40thread.v2/0?context=%7b%22Tid%22%3a%22e8a52afe-6ea8-47f7-b275-783f7087b5fa%22%2c%22Oid%22%3a%223fce7771-2c7e-49c6-999b-58c37f86a484%22%7d', '2022-02-04T12:30:50', '2022-02-04T17:30:50', '2022-02-03 15:17:33', '2022-02-03 15:17:33'),
(4, 1, 'Programming 1', 'http://teams.com', '2022-01-19T12:30:50', '2022-01-19T14:30:50', '2022-02-23 15:26:41', '2022-02-23 15:26:41'),
(5, 1, 'math1', 'https://zoom.com/', '2022-01-13T12:30:50', '2022-01-20T14:30:50', '2022-03-12 15:50:34', '2022-03-12 15:50:34');

-- --------------------------------------------------------

--
-- Table structure for table `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2014_10_12_100000_create_password_resets_table', 1),
(3, '2014_10_12_200000_add_two_factor_columns_to_users_table', 1),
(4, '2019_08_19_000000_create_failed_jobs_table', 1),
(5, '2019_12_14_000001_create_personal_access_tokens_table', 1),
(6, '2022_01_24_143439_create_sessions_table', 1),
(7, '2022_01_24_192444_create_attendances_table', 1),
(8, '2022_01_24_212048_create_courses_table', 1),
(9, '2022_01_24_212919_create_student_locations_table', 1);

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `abilities` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sessions`
--

CREATE TABLE `sessions` (
  `id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `ip_address` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_agent` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payload` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_activity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `sessions`
--

INSERT INTO `sessions` (`id`, `user_id`, `ip_address`, `user_agent`, `payload`, `last_activity`) VALUES
('cXlJ5VTrAr8Q6ApW4AgoyDgIozXiRtp0x4zSoyHE', 4, '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.1 Safari/605.1.15', 'YTo1OntzOjY6Il90b2tlbiI7czo0MDoicEtXRDRLRlU4UTVRS3p2eFhLeFlDeTlZU2tlV1BjSHphVG1QTFFsSyI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MjE6Imh0dHA6Ly8xMjcuMC4wLjE6ODAwMCI7fXM6NjoiX2ZsYXNoIjthOjI6e3M6Mzoib2xkIjthOjA6e31zOjM6Im5ldyI7YTowOnt9fXM6NTA6ImxvZ2luX3dlYl81OWJhMzZhZGRjMmIyZjk0MDE1ODBmMDE0YzdmNThlYTRlMzA5ODlkIjtpOjQ7czoxNzoicGFzc3dvcmRfaGFzaF93ZWIiO3M6NjA6IiQyeSQxMCR1Tnp3d2FkeU8xRC9IV3ZxcDZUaFNPdHlCTkE4RmJHbmFJOUh2Tlc5cWsyVlRnVW42TUFmbSI7fQ==', 1645634221),
('jOEQIR8S8exuF8rT6AWlsCqz8oa20EKagcqSW3fE', 6, '192.168.0.171', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.3 Safari/605.1.15', 'YTo1OntzOjY6Il90b2tlbiI7czo0MDoiS3lUclRadUFzY2pXMHQ5Y1RwbnhSRlpLQXpldHRaRzdKQ3c2SFIycSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDY6Imh0dHA6Ly8xOTIuMTY4LjAuMTcxOjgwMDAvYXR0ZW5kYW5jZXMvYXR0X2xpc3QiO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX1zOjUwOiJsb2dpbl93ZWJfNTliYTM2YWRkYzJiMmY5NDAxNTgwZjAxNGM3ZjU4ZWE0ZTMwOTg5ZCI7aTo2O3M6MTc6InBhc3N3b3JkX2hhc2hfd2ViIjtzOjYwOiIkMnkkMTAkU3VSRC9nd0NHcjNxRGpUbXpnRUZGLnNVc05PanltcnlnWGQ1V1JzWjdwRDNubEt4TDl6OVciO30=', 1647103900),
('LAUXVr1uxoHAxDjbtq1oGTChzorZEZ4bm8qe5BBi', 1, '172.20.10.3', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36', 'YTo1OntzOjY6Il90b2tlbiI7czo0MDoieEZaMllvWDFoMHZrNUx1cVJSbzZKSkpKY0dCazRQdERtMG9JTHRhMCI7czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzY6Imh0dHA6Ly8xNzIuMjAuMTAuMzo4MDAwL2NvdXJzZXMvbGlzdCI7fXM6NTA6ImxvZ2luX3dlYl81OWJhMzZhZGRjMmIyZjk0MDE1ODBmMDE0YzdmNThlYTRlMzA5ODlkIjtpOjE7czoxNzoicGFzc3dvcmRfaGFzaF93ZWIiO3M6NjA6IiQyeSQxMCRNL3JINjVxOTA2ejB2YnEvY0doYS5PdUN4UDNMQVk4Mko3ek5rMzh3QkN5QlRnY2hnOHdLcSI7fQ==', 1643905464),
('p9d601CFgcE2dRUUXomUP5woZBPooMcydtBJHkrZ', NULL, '192.168.1.14', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.1 Safari/605.1.15', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoidm92bklyT3NJUnhuUlFzRHZUQjJGeE5FRkFBYjEwRjFGeDFqYmRSdiI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MjQ6Imh0dHA6Ly8xOTIuMTY4LjEuMTQ6ODAwMCI7fXM6NjoiX2ZsYXNoIjthOjI6e3M6Mzoib2xkIjthOjA6e31zOjM6Im5ldyI7YTowOnt9fX0=', 1645634184);

-- --------------------------------------------------------

--
-- Table structure for table `student_locations`
--

CREATE TABLE `student_locations` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `student_id` bigint(20) UNSIGNED NOT NULL,
  `latitude` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `longtitude` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `addressline` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `student_locations`
--

INSERT INTO `student_locations` (`id`, `student_id`, `latitude`, `longtitude`, `addressline`, `created_at`, `updated_at`) VALUES
(1, 2, '52.1728769', '21.025382500000003', 'Batuty 16, 02-743 Warszawa, Poland', '2022-02-03 14:34:03', '2022-02-03 14:34:03'),
(2, 3, '52.1728769', '21.025382500000003', 'Batuty 16, 02-743 Warszawa, Poland', '2022-02-03 15:18:43', '2022-02-03 15:18:43');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `two_factor_secret` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `two_factor_recovery_codes` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `api_token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `two_factor_secret`, `two_factor_recovery_codes`, `role`, `api_token`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Iwona Dolinska', 'iwona@vistula.edu.com', '$2y$10$M/rH65q906z0vbq/cGha.OuCxP3LAY82J7zNk38wBCyBTgchg8wKq', NULL, NULL, '0', NULL, NULL, '2022-02-03 14:28:58', '2022-02-03 15:15:09'),
(2, 'A RAHMAN', 'rahman@gmail.com', '$2y$10$ufGnfvZXAGyB4Jtpk1OB9.27jgccTiNeO5ElVWmvoaSmfefnCqrtK', NULL, NULL, '1', NULL, NULL, '2022-02-03 14:33:28', '2022-02-03 15:11:54'),
(3, 'Rahman', 'rahman2@vistula.com', '$2y$10$yXGkFe9MPCvMlUkl.NLl0ufLDD868E48HDtaBjhu.5bsd54FeA6h2', NULL, NULL, '1', NULL, NULL, '2022-02-03 15:18:18', '2022-02-03 15:27:51'),
(4, 'rahmanumar', 'rahman.poland.ce21@gmail.com', '$2y$10$uNzwwadyO1D/HWvqp6ThSOtyBNA8FbGnaI9HvNW9qk2VTgUn6MAfm', NULL, NULL, '0', NULL, NULL, '2022-02-23 15:26:00', '2022-02-23 15:26:00'),
(5, 'A. Rahman4', 'rahman202@gmail.com', '$2y$10$9phDv6LiUXmCLQo4AlcXLu0j714YSCmG9/0MoVNQo1Xi4EVJxA8oy', NULL, NULL, '1', '5db663cde84cbc0250b5c52c5860ab1c.b6e8669d10f33744e068d8e745c4fdf1', NULL, '2022-03-12 15:42:01', '2022-03-12 15:42:16'),
(6, 'rangga', 'rangga@gmail.com', '$2y$10$SuRD/gwCGr3qDjTmzgEFF.sUsNOjymrygXd5WRsZ7pD3nlKxL9z9W', NULL, NULL, '0', NULL, NULL, '2022-03-12 15:50:04', '2022-03-12 15:50:04');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendances`
--
ALTER TABLE `attendances`
  ADD PRIMARY KEY (`id`),
  ADD KEY `attendances_student_id_foreign` (`student_id`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `courses_lecturer_id_foreign` (`lecturer_id`);

--
-- Indexes for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `password_resets`
--
ALTER TABLE `password_resets`
  ADD KEY `password_resets_email_index` (`email`);

--
-- Indexes for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Indexes for table `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sessions_user_id_index` (`user_id`),
  ADD KEY `sessions_last_activity_index` (`last_activity`);

--
-- Indexes for table `student_locations`
--
ALTER TABLE `student_locations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_locations_student_id_foreign` (`student_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`),
  ADD UNIQUE KEY `users_api_token_unique` (`api_token`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendances`
--
ALTER TABLE `attendances`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `student_locations`
--
ALTER TABLE `student_locations`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `attendances`
--
ALTER TABLE `attendances`
  ADD CONSTRAINT `attendances_student_id_foreign` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_lecturer_id_foreign` FOREIGN KEY (`lecturer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `student_locations`
--
ALTER TABLE `student_locations`
  ADD CONSTRAINT `student_locations_student_id_foreign` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
