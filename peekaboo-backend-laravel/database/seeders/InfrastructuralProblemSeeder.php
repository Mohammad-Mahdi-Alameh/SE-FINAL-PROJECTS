<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class InfrastructuralProblemSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('infrastructural_problems')->insert([
            'latitude' => 33.28772,
            'longitude' => 34.12345,
            'is_fixed' => 0,
            'false_infra' => 0,
            'accepted' => 0,
            'rejected' => 0,
            'pending' => 0,
            'type' => 'hole',
            'user_id' => 1,
        ]);
        DB::table('infrastructural_problems')->insert([
            'latitude' => 33.98772,
            'longitude' => 34.41145,
            'is_fixed' => 0,
            'false_infra' => 0,
            'accepted' => 0,
            'rejected' => 0,
            'pending' => 0,
            'type' => 'blockage',
            'user_id' => 1,
        ]);
        DB::table('infrastructural_problems')->insert([
            'latitude' => 35.28772,
            'longitude' => 34.12345,
            'is_fixed' => 0,
            'false_infra' => 0,
            'accepted' => 0,
            'rejected' => 0,
            'pending' => 0,
            'type' => 'turn',
            'user_id' => 1,
        ]);
        DB::table('infrastructural_problems')->insert([
            'latitude' => 33.18772,
            'longitude' => 34.12345,
            'is_fixed' => 0,
            'false_infra' => 0,
            'accepted' => 0,
            'rejected' => 0,
            'pending' => 0,
            'type' => 'bump',
            'user_id' => 1,
        ]);
        DB::table('infrastructural_problems')->insert([
            'latitude' => 33.34772,
            'longitude' => 34.12345,
            'is_fixed' => 0,
            'false_infra' => 0,
            'accepted' => 0,
            'rejected' => 0,
            'pending' => 0,
            'type' => 'hole',
            'user_id' => 1,
        ]);
    }
}
