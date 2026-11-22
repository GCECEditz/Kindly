package com.example.screens

// ============================================================
// UI-ONLY MOCK DATA for the Donation module (2.0).
// Hardcoded so the screens can be designed before the backend exists.
// TODO(2.x backend): replace with Supabase D5 (donations) and
// D6 (fundraising progress), then delete this file.
// ============================================================

data class DonationCampaign(
    val id: String,
    val ngoName: String,
    val ngoInitials: String,
    val title: String,
    val raised: Double,
    val goal: Double
)

data class MockDonation(
    val id: String,
    val ngoName: String,
    val ngoInitials: String,
    val campaignTitle: String,
    val amount: Double,
    val date: String,
    val status: String, // CONFIRMED / PENDING / RETRACTED
    val message: String
)

data class NgoTotal(val ngoName: String, val confirmed: Double, val fee: Double, val toNgo: Double)

// Confirmed totals sum to the RM 850.00 story; split = Kindly 5% / NGO 95% (gateway fees borne by NGO)
val mockNgoTotals = listOf(
    NgoTotal("Mount Miriam Cancer Hospital", 300.00, 15.00, 285.00),
    NgoTotal("Kawan", 200.00, 10.00, 190.00),
    NgoTotal("St. Nicholas Home, Penang", 250.00, 12.50, 237.50),
    NgoTotal("The Habitat Foundation", 100.00, 5.00, 95.00)
)
// TODO(2.6 backend): compute from Supabase D5 CONFIRMED donations and write totals into D6.

val mockCampaigns = listOf(
    DonationCampaign(
        id = "c1",
        ngoName = "Mount Miriam Cancer Hospital",
        ngoInitials = "MM",
        title = "Subsidised Cancer Treatment Fund",
        raised = 12500.00,
        goal = 25000.00
    ),
    DonationCampaign(
        id = "c2",
        ngoName = "The Habitat Foundation",
        ngoInitials = "HF",
        title = "Rainforest Restoration Programme",
        raised = 8900.00,
        goal = 15000.00
    ),
    DonationCampaign(
        id = "c3",
        ngoName = "St. Nicholas Home, Penang",
        ngoInitials = "SN",
        title = "Assistive Technology Classroom",
        raised = 21500.00,
        goal = 22000.00
    )
)

val mockDonations = listOf(
    MockDonation(
        id = "d1",
        ngoName = "Mount Miriam Cancer Hospital",
        ngoInitials = "MM",
        campaignTitle = "Subsidised Cancer Treatment Fund",
        amount = 50.00,
        date = "12 Nov 2026",
        status = "CONFIRMED",
        message = "Keep up the good work!"
    ),
    MockDonation(
        id = "d2",
        ngoName = "Kawan",
        ngoInitials = "KW",
        campaignTitle = "Community Meals for the Needy",
        amount = 200.00,
        date = "8 Nov 2026",
        status = "CONFIRMED",
        message = ""
    ),
    MockDonation(
        id = "d3",
        ngoName = "The Habitat Foundation",
        ngoInitials = "HF",
        campaignTitle = "Rainforest Restoration Programme",
        amount = 100.00,
        date = "3 Nov 2026",
        status = "PENDING",
        message = "For the trees!"
    ),
    MockDonation(
        id = "d4",
        ngoName = "St. Nicholas Home, Penang",
        ngoInitials = "SN",
        campaignTitle = "Assistive Technology Classroom",
        amount = 250.00,
        date = "28 Oct 2026",
        status = "CONFIRMED",
        message = ""
    ),
    MockDonation(
        id = "d5",
        ngoName = "Women's Centre for Change (WCC) Penang",
        ngoInitials = "WC",
        campaignTitle = "Safe Shelter Fund",
        amount = 150.00,
        date = "20 Oct 2026",
        status = "RETRACTED",
        message = ""
    )
)
