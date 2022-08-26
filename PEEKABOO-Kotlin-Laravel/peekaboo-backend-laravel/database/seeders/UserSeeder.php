<?php

namespace Database\Seeders;
use Illuminate\Support\Facades\DB;
use Illuminate\Database\Seeder;
use Illuminate\Support\Str;
use Illuminate\Support\Facades\Hash;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('users')->insert([
            'firstname' => 'peekaboo',
            'lastname' => 'user',
            'phonenumber' => 123456,
            'balance' => 0,
            'total_reports' => 0,
            'picture' => '',
            'username' => 'peekaboo user',
            'password' => Hash::make('@sefactory'),
            'is_admin' => '0',

        ]);
        DB::table('users')->insert([
            'firstname' => 'peekaboo',
            'lastname' => 'admin',
            'phonenumber' => 123456,
            'balance' => 0,
            'total_reports' => 0,
            'picture' => '',
            'username' => 'peekaboo admin',
            'password' => Hash::make('@sefactory'),
            'is_admin' => '1',

        ]);
    }
}
